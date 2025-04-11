package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.config.EmailSenderConfig;
import nbcc.restaurant.entities.DiningTable;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
import nbcc.restaurant.repositories.EventRepository;
import nbcc.restaurant.repositories.ReservationRequestRepository;
import nbcc.restaurant.repositories.SeatingRepository;
import nbcc.restaurant.services.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationRequestController {

    private final ReservationRequestRepository reservationRequestRepo;
    private final SeatingRepository seatingRepo;
    private final EventRepository eventRepo;

    private final EmailSenderConfig emailSenderConfig;
    private final EmailSenderService emailSenderService;

    private final Logger log = LoggerFactory.getLogger(ReservationRequestController.class);

    public ReservationRequestController(ReservationRequestRepository reservationRequestRepository, SeatingRepository seatingRepository, EventRepository eventRepository, EmailSenderConfig emailSenderConfig, EmailSenderService emailSenderService) {
        this.reservationRequestRepo = reservationRequestRepository;
        this.seatingRepo = seatingRepository;
        this.eventRepo = eventRepository;
        this.emailSenderConfig = emailSenderConfig;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping({ "/seating/reserve/{id}"})
    public String reserve(@PathVariable long id, Model model){

        var seatingDb= seatingRepo.findById(id);
        var eventDb= eventRepo.findById(seatingDb.getEvent().getId());

        var reservation = new ReservationRequest();
        reservation.setSeating(seatingDb);
        model.addAttribute("reservation", reservation);
        model.addAttribute("event", eventDb.get());
        model.addAttribute("seating", seatingDb);

        return "/reservationRequests/create";
    }

    @PostMapping({ "/seating/reserve/{seating_id}"})
    public String reserve(@ModelAttribute("reservation") @Valid ReservationRequest reservationRequest,
                                     BindingResult bindingResult,
                                     @PathVariable long seating_id, Model model){

        var seating = seatingRepo.findById(seating_id);
        var eventDb= seating.getEvent();

        reservationRequest.setSeating(seating);
        reservationRequest.setStatus(ReservationStatus.PENDING); // by default, is pending

        if (bindingResult.hasErrors()) {

            model.addAttribute("event", eventDb);
            model.addAttribute("seating", seating);
            return "reservationRequests/create";
        }

        reservationRequestRepo.save(reservationRequest);
        sendReservationEmail(reservationRequest);

        return "reservationRequests/reservationConfirmationPage";
       // return "redirect:/event/" + eventDb.getId();
    }

    private void sendReservationEmail(ReservationRequest reservationRequest) {
        try{
            if (reservationRequest.getEmail() != null && !reservationRequest.getEmail().isBlank()) {
                var subject = "Your Reservation Request Has Been Received";
                var text = "Hi " + reservationRequest.getFirstName() + " " + reservationRequest.getLastName() + ",\n\n" +
                        "Thank you for your booking request. We have successfully received your submission and it is currently under review.\n" +
                        "You will receive a follow-up email once a decision has been made — whether your request is approved or denied.\n\n" +
                        "Here are the details of your booking request:\n\n" +
                        "Reservation ID: " + reservationRequest.getId() + "\n\n" +
                        "Event Details:\n" +
                        "Name: " + reservationRequest.getSeating().getEvent().getName() + "\n" +
                        "Description: " + reservationRequest.getSeating().getEvent().getDescription() + "\n" +
                        "Layout: " + reservationRequest.getSeating().getEvent().getLayout().getName() + "\n" +
                        "Price: $" + reservationRequest.getSeating().getEvent().getPrice()+ "\n" +
                        "Event Dates: From " + reservationRequest.getSeating().getEvent().getStartDate() + " to " + reservationRequest.getSeating().getEvent().getEndDate() + "\n\n" +
                        "Reservation Details:\n" +
                        "Full Name: " + reservationRequest.getFirstName() + " " + reservationRequest.getLastName() + "\n" +
                        "Email Address: " + reservationRequest.getEmail() + "\n" +
                        "Group Size: " + reservationRequest.getGroupSize() + "\n" +
                        "reservation Date and Time: " + reservationRequest.getSeating().getSeatingDateTime() + "\n" +
                        "Duration: " + reservationRequest.getSeating().getSeatingDuration() + " minutes\n\n" +
                        "If you have any questions or need to make changes to your request, feel free to contact us at admin@xrayteam.ca\n\n" +
                        "Warm regards,\n" +
                        "X-Team Booking";

                emailSenderService.sendEmail(subject, text, emailSenderConfig.getDefaultFrom(), reservationRequest.getEmail());
            } else {
                log.warn("Email not provided for user {}", reservationRequest.getEmail());
            }
        } catch (Exception e){
            log.error("Error while sending reservation email", e);
        }
    }

    @GetMapping("/reservations")
    public String getAll(Long eventId, ReservationStatus status, Model model) {

        List<ReservationRequest> reservations;

        if (eventId != null && status != null) {
            reservations = reservationRequestRepo.findBySeating_Event_IdAndStatusOrderBySeating_SeatingDateTimeAsc(eventId, status);
        } else if (eventId != null) {
            reservations = reservationRequestRepo.findBySeating_Event_IdOrderBySeating_SeatingDateTimeAsc(eventId);
        } else if (status != null) {
            reservations = reservationRequestRepo.findByStatusOrderBySeating_SeatingDateTimeAsc(status);
        } else {
            reservations = reservationRequestRepo.findAllByOrderBySeating_SeatingDateTimeAsc();
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("events", eventRepo.findAll());
        model.addAttribute("selectedEvent", eventId);
        model.addAttribute("status", ReservationStatus.values());
        model.addAttribute("selectedStatus", status);

        return "/reservationRequests/index";
    }

    @GetMapping("/reservation/{id}")
    public String detail(Model model, @PathVariable long id, ReservationStatus status){

        var reservation = reservationRequestRepo.findById(id);

        if(reservation.isPresent()) { // this means the entity was found in the database
            var currentReservation = reservation.get();
            var seating = currentReservation.getSeating();

            var event = seating.getEvent();
            var layout = event.getLayout();
            var tables = layout.getDiningTables();

            List<ReservationRequest> allReservationsForEvent = reservationRequestRepo.findBySeating_Event_Id(event.getId());

            List<Long> usedTableIds = new ArrayList<>();
            for (ReservationRequest r : allReservationsForEvent) {
                if (r.getAssignedTable() != null &&
                    r.getStatus().equals(ReservationStatus.APPROVED) &&
                    r.getId() != currentReservation.getId()) { // excluding current reservation
                        usedTableIds.add(r.getAssignedTable().getId());
                }
            }

            List<DiningTable> availableTables = new ArrayList<>();
            for (DiningTable dt : tables) {
                if (!usedTableIds.contains(dt.getId()) ||
                        (currentReservation.getAssignedTable() != null && dt.getId() == currentReservation.getAssignedTable().getId())
                ) {
                    availableTables.add(dt);
                }
            }

            model.addAttribute("seating", seating);
            model.addAttribute("event", event);
            model.addAttribute("layout", layout);
            model.addAttribute("tables", availableTables);
            model.addAttribute("reservation", currentReservation);
            model.addAttribute("status", ReservationStatus.values());
            model.addAttribute("selectedStatus", status);

            if (reservation.get().getStatus() != null){
                model.addAttribute("statusString", reservation.get().getStatus().toString());
            } else {
                model.addAttribute("statusString", "");
            }

            return "/reservationRequests/detail";
        }
        return "redirect:/reservations";
    }

    @PostMapping("/reservation/{id}/edit")
    public String update(@PathVariable long id, @ModelAttribute("reservation") @Valid ReservationRequest reservation, BindingResult bindingResult, Model model){

        var currentReservation = reservationRequestRepo.findById(id).orElse(null);
        var selectedStatus = reservation.getStatus();

        if (currentReservation !=null) {
            var selectedTable = reservation.getAssignedTable();
            if (selectedStatus == ReservationStatus.APPROVED && selectedTable == null) {

                bindingResult.rejectValue("assignedTable", "error.assignedTable", "Must assign an assigned table");

                model.addAttribute("reservation", currentReservation);
                model.addAttribute("status", ReservationStatus.values());
                model.addAttribute("selectedStatus", selectedStatus);

                var seating = currentReservation.getSeating();
                var event = seating.getEvent();
                var layout = event.getLayout();
                var tables = layout.getDiningTables();
                model.addAttribute("seating", seating);
                model.addAttribute("event", event);
                model.addAttribute("layout", layout);
                model.addAttribute("tables", tables);

                return "/reservationRequests/detail";
            } else if (selectedStatus == ReservationStatus.PENDING) {
                currentReservation.setStatus(reservation.getStatus());
                currentReservation.setAssignedTable(null);
                reservationRequestRepo.save(currentReservation);
            } else if (selectedStatus == ReservationStatus.DENIED) {
                currentReservation.setStatus(reservation.getStatus());
                currentReservation.setAssignedTable(null);
                reservationRequestRepo.save(currentReservation);
                sendDeniedEmail(currentReservation);
            } else {
                currentReservation.setStatus(reservation.getStatus());
                currentReservation.setAssignedTable(reservation.getAssignedTable());
                reservationRequestRepo.save(currentReservation);
                sendApprovedEmail(currentReservation);

            }
        }
        return "redirect:/reservation/" + reservation.getId();
    }

    private void sendApprovedEmail(ReservationRequest reservationRequest) {
        try{
            if (reservationRequest.getEmail() != null && !reservationRequest.getEmail().isBlank()) {
                var subject = "Your Reservation Request Has Been Approved!";
                var text = "Hi " + reservationRequest.getFirstName() + " " + reservationRequest.getLastName() + ",\n\n" +
                        "Great news! Your reservation request for the " + reservationRequest.getSeating().getEvent().getName() + " has been approved.\n" +
                        "We look forward to welcoming you.\n" +
                        "Here are your confirmed booking details:\n\n" +
                        "Reservation ID: " + reservationRequest.getId() + "\n\n" +
                        "Event: " + reservationRequest.getSeating().getEvent().getName() + "\n" +
                        "Description: " + reservationRequest.getSeating().getEvent().getDescription() + "\n" +
                        "Layout: " + reservationRequest.getSeating().getEvent().getLayout().getName() + "\n" +
                        "Price: $" + reservationRequest.getSeating().getEvent().getPrice()+ "\n" +
                        "Full Name: " + reservationRequest.getFirstName() + " " + reservationRequest.getLastName() + "\n" +
                        "Email Address: " + reservationRequest.getEmail() + "\n" +
                        "Group Size: " + reservationRequest.getGroupSize() + "\n" +
                        "Reservation Date and Time: " + reservationRequest.getSeating().getSeatingDateTime() + "\n" +
                        "Duration: " + reservationRequest.getSeating().getSeatingDuration() + " minutes\n\n" +
                        "If you have any questions or need to make changes to your request, feel free to contact us at admin@xrayteam.ca\n\n" +
                        "Warm regards,\n" +
                        "X-Team Booking";

                emailSenderService.sendEmail(subject, text, emailSenderConfig.getDefaultFrom(), reservationRequest.getEmail());
            } else {
                log.warn("Email not provided for user {}", reservationRequest.getEmail());
            }
        } catch (Exception e){
            log.error("Error while sending reservation email", e);
        }
    }
    private void sendDeniedEmail(ReservationRequest reservationRequest) {
        try{
            if (reservationRequest.getEmail() != null && !reservationRequest.getEmail().isBlank()) {
                var subject = "Your Reservation Request Has Been Denied.";
                var text = "Dear " + reservationRequest.getFirstName() + " " + reservationRequest.getLastName() + ",\n\n" +
                        "Thank you for your interest in the " + reservationRequest.getSeating().getEvent().getName() + " event.\n" +
                        "We regret to inform you that your reservation request has been denied.\n" +
                        "This could be due to limited availability or other scheduling conflicts.\n" +
                        "We sincerely apologize for the inconvenience and encourage you to explore our other upcoming events.\n" +
                        "If you have any questions, feel free to reach out to us at admin@xrayteam.ca\n\n" +
                        "Warm regards,\n" +
                        "X-Team Booking";
                emailSenderService.sendEmail(subject, text, emailSenderConfig.getDefaultFrom(), reservationRequest.getEmail());
            } else {
                log.warn("Email not provided for user {}", reservationRequest.getEmail());
            }
        } catch (Exception e){
            log.error("Error while sending reservation email", e);
        }
    }

}
