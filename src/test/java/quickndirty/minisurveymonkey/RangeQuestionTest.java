package quickndirty.minisurveymonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeQuestionTest {

	RangeQuestion q = new RangeQuestion ("Test", 1 , 9) ;
	RangeResponse r = new RangeResponse(5, q);
	RangeResponse r2 = new RangeResponse(10, q);

	@Test
	void testAddResponseRangeResponse() {
		assertEquals(q.addResponse(r), true);
		assertEquals(q.addResponse(r2), false);
		
	}

	@Test
	void testSetMin() {
		q.setMin(6);
		assertEquals(q.getMin(), 6);
	}

	@Test
	void testGetMin() {
		assertEquals(q.getMin(), 1);
	}

	@Test
	void testSetMax() {
		q.setMax(6);
		assertEquals(q.getMax(), 6);
	}

	@Test
	void testGetMax() {
		assertEquals(q.getMax(), 9);
	}

}
