package quickndirty.minisurveymonkey;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes={MinisurveymonkeyApplication.class})
@AutoConfigureMockMvc
public class WebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testIntegration() throws Exception {
        //POST A SURVEY
        MvcResult surveyResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/survey")
                .contentType("application/json")
                .content("{\"name\":\"Test class survey\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse response = surveyResult.getResponse();
        String surveyLocation = response.getHeader("Location");

        //POST A QUESTION
        MvcResult questionResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question")
                .contentType("application/json")
                .content("{\"survey\":\"" + surveyLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"prompt\":\"This is a test question\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse questionResponse = questionResult.getResponse();
        String questionLocation = questionResponse.getHeader("Location");

        //POST A RESPONSE
        MvcResult responseResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/response")
                .contentType("application/json")
                .content("{\"question\":\"" + questionLocation + "\"" +
                        ",\"type\":\"TEXT\"," +
                        "\"answer\":\"This is a test response\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse responseResponse = responseResult.getResponse();
        String responseLocation = responseResponse.getHeader("Location");

        //GET THE SURVEY
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/survey/" + surveyLocation.replaceAll("[^\\d.]", ""))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test class survey")));

        //GET THE QUESTION
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/" + questionLocation.replaceAll("[^\\d.]", ""))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prompt", is("This is a test question")));

        //GET THE RESPONSE
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/response/" + responseLocation.replaceAll("[^\\d.]", ""))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer", is("This is a test response")));

        //DELETE THE RESPONSE
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/response/" + responseLocation.replaceAll("[^\\d.]", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //DELETE THE QUESTION
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/question/" +questionLocation.replaceAll("[^\\d.]", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //DELETE THE SURVEY
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/survey/" +surveyLocation.replaceAll("[^\\d.]", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}