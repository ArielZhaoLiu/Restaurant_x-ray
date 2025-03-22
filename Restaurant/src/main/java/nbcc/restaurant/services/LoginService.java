package nbcc.restaurant.services;

public interface LoginService {
    boolean login(String username, String password);
    void logout();
    boolean IsCurrenLoginValid();
    void updateLastActivity();
}
