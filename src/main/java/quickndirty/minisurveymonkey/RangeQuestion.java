package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class RangeQuestion extends Question{
	private int min;
	private int max;
	
	public RangeQuestion(){
		type = QuestionType.NUMBER;
	}

	public RangeQuestion(String Prompt, int min, int max) {
		type = QuestionType.NUMBER;
		this.prompt = Prompt;
		this.min = min;
		this.max = max;
		
		
	}
	
	public boolean addResponse(RangeResponse r) {
		if(r.getAnswer() < min || r.getAnswer() > max) {
			return false;
		}else {
			Response q = (Response)r;
			return true;
			
		}
	}
	
	public void setMin(int m) {
		min = m;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMax(int m) {
		max = m;
	}
	
	public int getMax() {
		return max;
	}
}
