package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TextResponseTest {
	TextQuestion t = new TextQuestion("Test");
	TextResponse r = new TextResponse("Testing", t);


	@Test
	void testAnswer() {
		assertEquals(r.getAnswer(), "Testing");
		r.setAnswer("Changed");
		assertEquals(r.getAnswer(), "Changed");
	}

	

}
