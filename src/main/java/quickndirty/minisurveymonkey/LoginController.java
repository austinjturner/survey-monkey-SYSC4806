package quickndirty.minisurveymonkey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public User user(@AuthenticationPrincipal OAuth2User principal) {
        User user = userRepository.findByExternalID(principal.getName());

        // If this is a new user, save to db
        if (user == null){
            user = new User();
            System.out.println(principal.getName());
            user.setExternalID(principal.getName());
            user.setName(principal.getAttribute("name"));
            System.out.println(user.getName());
            userRepository.save(user);
        }
        return user;
    }
}
