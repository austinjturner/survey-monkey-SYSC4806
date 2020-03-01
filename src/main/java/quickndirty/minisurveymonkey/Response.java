package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class Response {
	protected int ID;
	protected QType type;
	protected Question question;
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question q) {
		question = q;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int i) {
		ID=i;
	}
	
	public void setType(QType q) {
		type = q;
	}
	
	public QType getType() {
		return type;
	}
}
