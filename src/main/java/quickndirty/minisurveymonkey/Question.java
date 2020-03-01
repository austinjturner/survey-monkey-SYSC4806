package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class Question {
	protected String prompt;
	protected int ID;
	protected QType type;
	
	public void setPrompt(String p) {
		prompt = p;
	}
	
	public String getPrompt() {
		return prompt;
	}
	
	public void setID(int i) {
		ID = i;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setType(QType q) {
		type = q;
	}
	
	public QType getType() {
		return type;
	}
}
