package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveyTests {
    Survey survey = new Survey();

    @Test
    public void isEmpty(){
        assertEquals(0, survey.getQuestions().size());
    }
}
