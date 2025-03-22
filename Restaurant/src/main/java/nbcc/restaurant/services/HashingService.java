package nbcc.restaurant.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingService {

    private final PasswordEncoder passwordEncoder;

    public HashingService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hash(String data){
        var encoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(data);
    }

    public boolean valid(String plainText, String hashedValue){
        var encoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainText, hashedValue);
    }

}
