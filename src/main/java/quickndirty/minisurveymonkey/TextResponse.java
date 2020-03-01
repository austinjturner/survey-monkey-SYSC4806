package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class TextResponse extends Response{
	
	private String Answer;
	
	public TextResponse(String r) {
		Answer = r;
		type = QType.TEXT;
	}
	
	public String getResponse() {
		return Answer;
	}
	
	public void setResponse(String r) {
		Answer = r;
	}

}
