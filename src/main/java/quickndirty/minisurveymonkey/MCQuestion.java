package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class MCQuestion extends Question{
	private String[] choices;
	public MCQuestion() {
		type = QuestionType.MC;
		
	}
	
	public MCQuestion(String Prompt, String[] choices) {
		this.choices = choices;
		type = QuestionType.MC;
		this.prompt = Prompt;
	}
	
	public boolean addResponse(MCResponse r) {
		if(r.getAnswer() < 0 || r.getAnswer() > getMax()) {
			return false;
		}else {
			responses.add((Response)r);
			return true;
		}
	}
	
	public String getAnswer(int index) {
		if(index <0 || index > getMax()) {
			return "Error:  Invalid Choice Index";
		}else {
			return choices[index];
		}
		
	}
	
	public boolean setAnswer(String s, int index) {
		if(index <0 || index > getMax()) {
			return false;
		}else {
			choices[index] = s;
			return true;
		}
	}
	
	public int getMax() {
		return choices.length-1;
	}
}
