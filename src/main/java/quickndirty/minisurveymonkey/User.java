package quickndirty.minisurveymonkey;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_login")  // USER is reserved in SQL (or at least in postgres)
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="USER_SEQ_GEN")
    @SequenceGenerator(name="USER_SEQ_GEN", sequenceName="USER_SEQ_GEN")
    protected int ID;

    private String externalID;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    @JsonIgnore
    protected List<Survey> surveys;

    public String getExternalID(){
        return this.externalID;
    }

    public void setExternalID(String externalID){
        this.externalID = externalID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Survey> getSurveys(){
        return this.surveys;
    }

    public void setSurveys(List<Survey> surveys){
        this.surveys = surveys;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }
}
