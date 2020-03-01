package quickndirty.minisurveymonkey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Survey {

    @Id
    protected int ID;
    @OneToMany
    protected ArrayList<Question> questions;

    public Survey() {
        questions = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question q){
        this.questions.add(q);
    }

    public void removeQuestion(Question q){
        this.questions.remove(q);
    }

    public int size(){
        return this.questions.size();
    }

    public String toString(){
        String s = "Survey Questions:\n";
        for(Question q: questions){
            s+=q.toString();
            s+="\n";
        }
        return s;

    }



}


