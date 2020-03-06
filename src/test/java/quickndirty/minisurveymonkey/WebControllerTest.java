package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkCreateSurveyPageStatus() throws Exception {
        this.mockMvc.perform(get("/create-survey")).andExpect(status().isOk());
    }

    @Test
    public void checkAnswerSurveyPageStatus() throws Exception {
        this.mockMvc.perform(get("/survey/1")).andExpect(status().isOk());
    }
}