package nbcc.restaurant.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Seating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd-mm")
    private LocalDate seatingDateTime;

    @NotNull
    private int seatingDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id", foreignKey = @ForeignKey(name="FK_EVENT_SEATING"))
    private Event event;

    public Seating() {
    }

    public Seating(LocalDate seatingDateTime, int seatingDuration, Event event) {
        this.seatingDateTime = seatingDateTime;
        this.seatingDuration = seatingDuration;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getSeatingDateTime() {
        return seatingDateTime;
    }

    public void setSeatingDateTime(LocalDate seatingDateTime) {
        this.seatingDateTime = seatingDateTime;
    }

    public int getSeatingDuration() {
        return seatingDuration;
    }

    public void setSeatingDuration(int seatingDuration) {
        this.seatingDuration = seatingDuration;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
