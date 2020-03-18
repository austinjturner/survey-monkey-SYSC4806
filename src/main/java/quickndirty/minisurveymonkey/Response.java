package quickndirty.minisurveymonkey;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import javax.persistence.*;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		visible = true
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = TextResponse.class, name = "TEXT"),
		@JsonSubTypes.Type(value = RangeResponse.class, name = "NUMBER"),
		@JsonSubTypes.Type(value = MultipleChoiceResponse.class, name = "MC")
})
@Entity
public abstract class Response {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="RESPONSE_SEQ_GEN")
	@SequenceGenerator(name="RESPONSE_SEQ_GEN", sequenceName="RESPONSE_SEQ_GEN")
	protected int ID;
	protected QuestionType type;
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
	
	public void setType(QuestionType q) {
		type = q;
	}
	
	public QuestionType getType() {
		return type;
	}

	
}
