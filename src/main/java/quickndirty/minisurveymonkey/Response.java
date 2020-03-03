package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

import javax.persistence.*;

@Entity
public class Response {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="RESPONSE_SEQ_GEN")
	@SequenceGenerator(name="RESPONSE_SEQ_GEN", sequenceName="RESPONSE_SEQ_GEN")
	protected int ID;
	protected QType type;
	@ManyToOne(cascade = CascadeType.ALL)
	protected Question question;

	public Response(){}
	
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
