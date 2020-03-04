package quickndirty.minisurveymonkey;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		visible = true
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = TextQuestion.class, name = "TEXT")
})
@Entity
public abstract class Question {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="QUESTION_SEQ_GEN")
	@SequenceGenerator(name="QUESTION_SEQ_GEN", sequenceName="QUESTION_SEQ_GEN")
	protected int ID;
	@ManyToOne(cascade = CascadeType.ALL)
	private Survey survey;
	protected QuestionType type;
	protected String prompt;

	public Question() {

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
	
	public void setType(QuestionType q) {
		type = q;
	}
	
	public QuestionType getType() {
		return type;
	}

	public Survey getSurvey(){
		return this.survey;
	}

	public void setSurvey(Survey survey){
		this.survey = survey;
	}
}
