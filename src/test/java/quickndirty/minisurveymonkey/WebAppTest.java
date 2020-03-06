package quickndirty.minisurveymonkey;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes={MinisurveymonkeyApplication.class})
@AutoConfigureMockMvc
public class WebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testCreateSurvey() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/survey")
                .contentType("application/json")
                .content("{name: 'ThisIsFromTestClass'}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"info\":[],\"id\":1}")));
    }

    @Test
    @Order(2)
    public void testCreateQuestion() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/survey")
                .contentType("application/json")
                .content("{name: 'ThisIsFromTestClass'}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"info\":[],\"id\":1}")));
    }

    @Test
    @Order(3)
    public void testCreateResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/survey")
                .contentType("application/json")
                .content("{name: 'ThisIsFromTestClass'}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"info\":[],\"id\":1}")));
    }

    @Test
    @Order(4)
    public void testGetSurveys_none() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/survey")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    @Order(5)
    public void testGetQuestions_none() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/question")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    @Order(6)
    public void testGetResponses_none() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/response")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
}