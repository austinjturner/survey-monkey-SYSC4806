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
        String textPrompt = "Text Prompt";
        String rangePrompt = "Range Prompt";
        String mcPrompt = "MC Prompt";
        String surveyResponse1 = "Answer 1";
        String surveyResponse2 = "Answer 2";
        String surveyResponse3 = "5432";
        String surveyResponse4 = "6789";
        String surveyResponse5 = "BestChoice";
        String surveyResponse6 = "WorstChoice";

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

        //POST A TEXT QUESTION
        MvcResult textQuestion = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question")
                .contentType("application/json")
                .content("{\"survey\":\"" + surveyLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"prompt\":\"" + textPrompt  + "\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse textQuestionResponse = textQuestion.getResponse();
        String textQuestionLocation = textQuestionResponse.getHeader("Location");

        // Add first response to text question
        MvcResult textResponse1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + textQuestionLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"answer\":\"" + surveyResponse1 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        // Add second response to text question
        MvcResult textResponse2 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + textQuestionLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"answer\":\"" + surveyResponse2 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        //POST A RANGE QUESTION
        MvcResult rangeQuestion = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question")
                .contentType("application/json")
                .content("{\"survey\":\"" + surveyLocation + "\"" +
                        ",\"type\":\"NUMBER\"" +
                        ",\"min\":\"0\"" +
                        ",\"max\":\"10000\"" +
                        ",\"prompt\":\"" + rangePrompt  + "\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse rangeQuestionResponse = rangeQuestion.getResponse();
        String rangeQuestionLocation = rangeQuestionResponse.getHeader("Location");

        // Add first response to range question
        MvcResult rangeResult1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + rangeQuestionLocation + "\"" +
                        ",\"type\":\"NUMBER\"," +
                        "\"answer\":\"" + surveyResponse3 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        // Add second response to range question
        MvcResult rangeResult2 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + rangeQuestionLocation + "\"" +
                        ",\"type\":\"NUMBER\"," +
                        "\"answer\":\"" + surveyResponse4 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        //POST A MULTIPLE CHOICE QUESTION
        MvcResult mcQuestion = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question")
                .contentType("application/json")
                .content("{\"survey\":\"" + surveyLocation + "\"" +
                        ",\"type\":\"MC\"" +
                        ",\"choices\": [\"BestChoice\", \"WorstChoice\"]" +
                        ",\"prompt\":\"" + mcPrompt  + "\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mcQuestionResponse = mcQuestion.getResponse();
        String mcQuestionLocation = mcQuestionResponse.getHeader("Location");

        // Add first response to mc question
        MvcResult mcResult1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + mcQuestionLocation + "\"" +
                        ",\"type\":\"MC\"," +
                        "\"answer\":\"" + surveyResponse5 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();

        // Add second response to mc question
        MvcResult mcResult2 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + mcQuestionLocation + "\"" +
                        ",\"type\":\"MC\"," +
                        "\"answer\":\"" + surveyResponse6 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        // Add third response to mc question
        MvcResult mcResult3 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + mcQuestionLocation + "\"" +
                        ",\"type\":\"MC\"," +
                        "\"answer\":\"" + surveyResponse6 +"\"}")
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isCreated())
                .andReturn();
        // Add fourth response to mc question
        MvcResult mcResult4 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + mcQuestionLocation + "\"" +
                        ",\"type\":\"MC\"," +
                        "\"answer\":\"" + surveyResponse6 +"\"}")
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
                .andExpect(content().string(containsString(surveyResponse2)))
                .andExpect(content().string(containsString(surveyResponse3)))
                .andExpect(content().string(containsString(surveyResponse4)))
                .andExpect(content().string(containsString(surveyResponse5)))
                .andExpect(content().string(containsString(surveyResponse6)))
                .andExpect(content().string(containsString("3")));

        //DELETE THE SURVEY
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/survey/" +surveyLocation.replaceAll("[^\\d.]", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(authentication(getOauthAuthenticationFor(principal))))
                .andExpect(status().isNoContent());
    }
}