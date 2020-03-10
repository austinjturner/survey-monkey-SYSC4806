package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeResponseTest {

	RangeQuestion q = new RangeQuestion ("Test", 1 , 9) ;
	RangeResponse r = new RangeResponse(5, q);

	@Test
	void testGetAnswer() {
		assertEquals(r.getAnswer(), 5);
	}

	@Test
	void testSetAnswer() {
		assertEquals(r.setAnswer(0), false);
		assertEquals(r.getAnswer(), 5);
		assertEquals(r.setAnswer(6), true);
		assertEquals(r.getAnswer(), 6);
	}

}
