package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static quickndirty.minisurveymonkey.OAuthUtils.getOauthAuthenticationFor;


@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private OAuth2User principal;

    @BeforeEach
    public void setUpUser() {
        principal = OAuthUtils.createOAuth2User(
                "Pepe Silvia", "psilvia@mailroom.gov");
    }

    @Test
    public void checkCreateSurveyPageStatus() throws Exception {
        this.mockMvc.perform(get("/create-survey")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isOk());
    }

    @Test
    public void checkAnswerSurveyPageStatus() throws Exception {
        this.mockMvc.perform(get("/survey/1")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isOk());
    }

    @Test
    public void checkSurveyResponsesPageStatus() throws Exception {
        this.mockMvc.perform(get("/survey/1/responses")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isOk());
    }

    @Test
    public void checkNoAuthenticationReturnsError() throws Exception {
        this.mockMvc.perform(get("/survey/1"))
                .andExpect(status().is(401));
    }
}