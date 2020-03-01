package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class TextResponse extends Response{
	
	private String Answer;
	
	public TextResponse(String r, Question q) {
		Answer = r;
		type = QType.TEXT;
		question = q;
	}
	
	public String getResponse() {
		return Answer;
	}
	
	public void setResponse(String r) {
		Answer = r;
	}

}
