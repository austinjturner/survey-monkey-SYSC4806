package quickndirty.minisurveymonkey;

import quickndirty.minisurveymonkey.QuestionTypes.QType;

import java.util.ArrayList;
import java.util.List;

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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	protected List<Response> responses;


	public Question(){
		responses = new ArrayList<Response>();
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
	
	public void setSurvey(Survey s) {
		survey = s;
	}
	
	public Survey getSurvey() {
		return survey;
	}
	
	public void addResponse(Response r) {
		responses.add(r);
	}
	
	public List<Response> getResponses() {
		return responses;
	}
	

}
