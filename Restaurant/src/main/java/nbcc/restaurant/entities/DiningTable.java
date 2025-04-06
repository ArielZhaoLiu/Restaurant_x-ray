package nbcc.restaurant.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(1)
    private int numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id", foreignKey = @ForeignKey(name = "FK_DININGTABLE_LAYOUT"))
    private Layout layout;

    @OneToMany(mappedBy = "assignedTable", fetch = FetchType.LAZY)
    private List<ReservationRequest> reservationRequests;

    private boolean archived = false;

    public DiningTable() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public List<ReservationRequest> getReservationRequests() {
        return reservationRequests;
    }

    public void setReservationRequests(List<ReservationRequest> reservationRequests) {
        this.reservationRequests = reservationRequests;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
