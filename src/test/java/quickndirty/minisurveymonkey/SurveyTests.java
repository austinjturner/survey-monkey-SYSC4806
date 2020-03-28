package quickndirty.minisurveymonkey;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SurveyTests {
    Survey survey = new Survey("SurveyTest");
    List<Question> questions = new ArrayList<>();
    Question question1 = new TextQuestion();
    Question question2 = new TextQuestion();

    @Test
    public void isEmpty(){
        assertEquals(0, survey.size());
    }

    @Test
    public void isClosed(){
        assertFalse(survey.getClosed());
    }

    @Test
    public void getName(){
        assertEquals("SurveyTest", survey.getName());
    }

    @Test
    public void addQuestion(){
        survey.addQuestion(question1);
        assertEquals(survey.getQuestions().get(0), question1);
    }

    @Test
    public void removeQuestion(){
        survey.removeQuestion(question1);
        assertEquals(0, survey.size());
    }

    @Test
    public void setQuestions(){
        questions.add(question1);
        questions.add(question2);
        survey.setQuestions(questions);
        assertEquals(questions, survey.getQuestions());
    }

    @Test
    public void close(){
        survey.setClosed();
        assertTrue(survey.getClosed());
    }



}
