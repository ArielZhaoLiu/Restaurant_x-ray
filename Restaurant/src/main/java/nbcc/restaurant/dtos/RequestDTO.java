package nbcc.restaurant.dtos;

public class RequestDTO {


    private long id;
    private long seating_id;
    private long event_id;
    private String firstName;
    private String lastName;
    private String email;
    private int groupSize;

    public RequestDTO() {
    }

    public RequestDTO(long id, long seating_id, long event_id, String firstName, String lastName, String email, int groupSize) {
        this.id = id;
        this.seating_id = seating_id;
        this.event_id = event_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeating_id() {
        return seating_id;
    }

    public void setSeating_id(long seating_id) {
        this.seating_id = seating_id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String toString() {
       String request =      "id="+this.getId()
               +", seating_id="+this.getSeating_id()
               +", event_id="+this.getEvent_id()
               +", firstName="+this.getFirstName()
               +", lastName="+this.getLastName()
               +", email="+this.getEmail()
               +", groupSize="+this.getGroupSize();
        return request;
    }
}
