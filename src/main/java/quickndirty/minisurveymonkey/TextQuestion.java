package quickndirty.minisurveymonkey;

import java.util.ArrayList;
import java.util.List;

public class TextQuestion extends Question {

	public TextQuestion(String Prompt) {
		type = QuestionType.TEXT;
		this.prompt = Prompt;
		
		
	}
}
