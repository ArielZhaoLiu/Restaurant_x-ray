package nbcc.restaurant.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Seating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime seatingDateTime;

    @NotNull
    private int seatingDuration;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id", foreignKey = @ForeignKey(name="FK_EVENT_SEATING"))
    private Event event;

    public Seating() {
    }

    public Seating(LocalDateTime seatingDateTime, int seatingDuration, Event event) {
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

    public LocalDateTime getSeatingDateTime() {
        return seatingDateTime;
    }

    public void setSeatingDateTime(LocalDateTime seatingDateTime) {
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
