package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResponseTest {
	TextQuestion t = new TextQuestion("Test");
	TextResponse r = new TextResponse("Testing", t);

	@Test
	void testGetQuestion() {
		assertEquals(r.getQuestion(), t);
	}

	@Test
	void testSetQuestion() {
		TextQuestion y = new TextQuestion("More Test");
		r.setQuestion(y);
		assertEquals(r.getQuestion(), y);
		assertFalse(r.getQuestion() == t);
	}

	

	@Test
	void testID() {
		r.setID(13);
		assertEquals(r.getID(), 13);
	}

	@Test
	void testSetType() {
		r.setType(QuestionType.MC);
		assertEquals(r.getType(), QuestionType.MC);
	}

	@Test
	void testGetType() {
		assertEquals(r.getType(), QuestionType.TEXT);
	}

}
