package quickndirty.minisurveymonkey;


import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class TextQuestion extends Question{
	

	public TextQuestion(String Prompt) {
		type = QType.TEXT;
		this.prompt = Prompt;
		
	}
}
