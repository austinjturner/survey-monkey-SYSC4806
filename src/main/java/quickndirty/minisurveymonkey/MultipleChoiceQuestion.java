package quickndirty.minisurveymonkey;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MultipleChoiceQuestion extends Question{

    @ElementCollection
    private List<String> choices;

    public MultipleChoiceQuestion(){
        this.type = QuestionType.MC;
        choices = new ArrayList<>();
    }

    public MultipleChoiceQuestion(String prompt, List<String> choices) {
        this.type = QuestionType.MC;
        this.prompt = prompt;
        this.choices = choices;
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public void removeChoice(int choiceNumber) {
        choices.remove(choiceNumber);
    }

    public List<String> getChoices() {
        return choices;
    }
}
