package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class QuestionTest {
	
	TextQuestion t = new TextQuestion("Test");
	Survey s = new Survey("Test Survey");
	TextResponse r = new TextResponse("Testing", t);
	

	

	@Test
	void testSetPrompt() {
		t.setPrompt("This is different now");
		assertEquals(t.getPrompt(), "This is different now");
	}

	@Test
	void testGetPrompt() {
		assertEquals(t.getPrompt(), "Test");
	}

	@Test
	void testID() {
		t.setID(12);
		assertEquals(t.getID(), 12);
	}


	@Test
	void testSetType() {
		t.setType(QuestionType.NUMBER);
		assertTrue(t.getType() == QuestionType.NUMBER);
	}

	@Test
	void testGetType() {
		assertTrue(t.getType() == QuestionType.TEXT);
	}

	@Test
	void testSurvey() {
		t.setSurvey(s);
		assertEquals(t.getSurvey().getName(), "Test Survey");
	}

	

	@Test
	void testResponse() {
		t.addResponse(r);
		assertEquals(t.getResponses().get(0),r);
	}

	

}
