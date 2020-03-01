package quickndirty.minisurveymonkey;

import java.util.*;
import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class TextQuestion extends Question{
	private ArrayList<TextResponse> answers;
	
	
	public TextQuestion(String Prompt) {
		type = QType.TEXT;
		this.prompt = Prompt;
		
	}
	
	public void addAnswer(TextResponse t) {
		answers.add(t);
	}
	
	public String generateResponses() {
		String r = "";
		for(int i = 0 ; i < answers.size() ; i++) {
			r = r+answers.get(i).getResponse()+"/n";
		}
		return r;
	}
}
