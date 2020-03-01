package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

public class Response {
	protected int ID;
	protected QType type;
	
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
