package quickndirty.minisurveymonkey;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static quickndirty.minisurveymonkey.OAuthUtils.getOauthAuthenticationFor;

@SpringBootTest(classes={MinisurveymonkeyApplication.class})
@AutoConfigureMockMvc
public class ResponseDisplayTest {

    @Autowired
    private MockMvc mockMvc;
    private OAuth2User principal;

    @BeforeEach
    public void setUpUser() {
        principal = OAuthUtils.createOAuth2User(
                "Pepe Silvia", "psilvia@mailroom.gov");
    }

    @Test
    @Order(1)
    public void testIntegration() throws Exception {
        String surveyName = "Test Survey AAA";
        String surveyResponse1 = "Answer 1";
        String surveyResponse2 = "Answer 2";

        //POST A SURVEY
        MvcResult surveyResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/survey")
                .contentType("application/json")
                .content("{\"name\":\"Test Survey AAA\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse response = surveyResult.getResponse();
        String surveyLocation = response.getHeader("Location");
        String[] surveyArr = surveyLocation.split("/");
        String surveyID = surveyArr[surveyArr.length - 1];

        //POST A QUESTION
        MvcResult questionResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question")
                .contentType("application/json")
                .content("{\"survey\":\"" + surveyLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"prompt\":\"" + surveyName  + "\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse questionResponse = questionResult.getResponse();
        String questionLocation = questionResponse.getHeader("Location");

        //POST 2 RESPONSES
        MvcResult responseResult1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + questionLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"answer\":\"" + surveyResponse1 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult responseResult2 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + questionLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"answer\":\"" + surveyResponse2 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        //GET THE SURVEY RESPONSE PAGE
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/survey/" + surveyID + "/responses")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(surveyName)))
                .andExpect(content().string(containsString(surveyResponse1)))
                .andExpect(content().string(containsString(surveyResponse2)));

        //DELETE THE SURVEY
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/survey/" +surveyLocation.replaceAll("[^\\d.]", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isNoContent());
    }
}