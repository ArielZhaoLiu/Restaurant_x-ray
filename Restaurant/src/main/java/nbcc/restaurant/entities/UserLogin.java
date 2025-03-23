package nbcc.restaurant.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UserLogin {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name="FK_USER_LOGIN"))
    private UserDetail userDetail;

    @Column(nullable = false)
    private LocalDateTime lastUsed;


    private LocalDateTime loggedOut;

    @Column(nullable = false)
    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    public UserLogin() {
    }

    public UserLogin(UserDetail userDetail) {
        this.userDetail = userDetail;
        this.active = true;
        this.lastUsed = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lasActivity) {
        this.lastUsed = lasActivity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(LocalDateTime loggedOut) {
        this.loggedOut = loggedOut;
    }
}
