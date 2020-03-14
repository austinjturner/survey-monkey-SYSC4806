package quickndirty.minisurveymonkey;

import javax.persistence.Entity;

@Entity
public class MultipleChoiceResponse extends Response{
    private String answer;

    public MultipleChoiceResponse() {
        this.type = QuestionType.MC;
    }

    public MultipleChoiceResponse(String answer, Question q) {
        this.type = QuestionType.MC;
        this.answer = answer;
        this.question = q;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }
}
