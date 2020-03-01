package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

import javax.persistence.*;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="QUESTION_SEQ_GEN")
	@SequenceGenerator(name="QUESTION_SEQ_GEN", sequenceName="QUESTION_SEQ_GEN")
	protected int ID;
	@ManyToOne(cascade = CascadeType.ALL)
	private Survey survey;
	protected QType type;
	protected String prompt;


	public Question(){

	}
	
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
