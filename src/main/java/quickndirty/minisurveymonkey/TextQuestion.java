package quickndirty.minisurveymonkey;

public class TextQuestion extends Question {
	public TextQuestion(String Prompt) {
		type = QuestionType.TEXT;
		this.prompt = Prompt;
		
	}
}
