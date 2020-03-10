package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class RangeResponse extends Response {
	private int answer;
	
	public RangeResponse(){
		type = QuestionType.NUMBER;
	}
	
	public RangeResponse(int a, Question q) {
		type = QuestionType.NUMBER;
		answer = a;
		question = q;
		
	}
	
	public int getAnswer() {
		return answer;
	}

	public boolean setAnswer(int answer) {
		RangeQuestion r = (RangeQuestion) question;
		if(answer < r.getMin() || answer > r.getMax() ) {
			return false;
		}else {
			this.answer = answer;
			return true;
		}
		
	}
	
}
