package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion q;
    private List<String> choices = new ArrayList<String>();

    @Test
    public void testDefaultConstructor() {
        MultipleChoiceQuestion q = new MultipleChoiceQuestion();
        assertEquals(q.getType(), QuestionType.MC);
        assertNotNull(q.getChoices());
    }

    @Test
    public void testGetAndAdd() {
        choices.add("Yes");
        choices.add("No");
        q = new MultipleChoiceQuestion("Can I offer you a nice egg in this trying time?", choices);
        assertEquals(q.getType(), QuestionType.MC);
        assertEquals(choices, q.getChoices());
        q.addChoice("Maybe");
        assertEquals(3, q.getChoices().size());
    }
}