package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class TextQuestion extends Question {
	public TextQuestion(){
		type = QuestionType.TEXT;
	}

	public TextQuestion(String Prompt) {
		type = QuestionType.TEXT;
		this.prompt = Prompt;
		
	}
}
