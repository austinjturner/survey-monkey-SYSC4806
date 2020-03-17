package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class MCResponse extends Response{
	private int answer;
	
	public MCResponse() {
		type = QuestionType.MC;
	}
	
	public MCResponse(int answer, Question q) {
		type = QuestionType.MC;
		this.answer = answer;
		this.question = q;
	}
	
	public int getAnswer() {
		return answer;
	}

	public boolean setAnswer(int answer) {
		MCQuestion r = (MCQuestion) question;
		if(answer < 0 || answer > r.getMax() ) {
			return false;
		}else {
			this.answer = answer;
			return true;
		}
		
	}
}
