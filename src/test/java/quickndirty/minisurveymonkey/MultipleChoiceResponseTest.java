package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceResponseTest {

    private MultipleChoiceResponse r;

    @Test
    void testDefaultConstructor() {
        MultipleChoiceResponse response = new MultipleChoiceResponse();
        assertEquals(response.getType(), QuestionType.MC);
    }

    @Test
    void testAnswer() {
        MultipleChoiceQuestion q = new MultipleChoiceQuestion();
        String answer = "I chose this";
        String newAnswer = "I chose this after";
        r = new MultipleChoiceResponse(answer, q);
        assertEquals(r.getType(), QuestionType.MC);
        assertEquals(answer, r.getAnswer());
        r.setAnswer(newAnswer);
        assertEquals(newAnswer, r.getAnswer());
    }
}
