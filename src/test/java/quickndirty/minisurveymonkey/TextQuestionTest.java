package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class TextQuestionTest {

	@Test
	void testTextQuestion() {
		TextQuestion t = new TextQuestion("Test");
		assertTrue(t.getType() == QuestionType.TEXT);
	}

}
