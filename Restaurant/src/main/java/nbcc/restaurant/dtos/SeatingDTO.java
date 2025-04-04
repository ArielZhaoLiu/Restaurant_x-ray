package nbcc.restaurant.dtos;

import java.time.LocalDateTime;


public class SeatingDTO {


    private long id;
    private LocalDateTime seatingDateTime;
    private int seatingDuration;

    public SeatingDTO() {
    }

    public SeatingDTO(long id, LocalDateTime seatingDateTime, int seatingDuration) {
        this.id = id;
        this.seatingDateTime = seatingDateTime;
        this.seatingDuration = seatingDuration;
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
}
