package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class TextResponse extends Response {
	
	private String answer;

	public TextResponse(){
		type = QuestionType.TEXT;
	}

	public TextResponse(String r, Question q) {
		answer = r;
		type = QuestionType.TEXT;
		question = q;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
