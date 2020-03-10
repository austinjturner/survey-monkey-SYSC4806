package quickndirty.minisurveymonkey;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_login")  // USER is reserved in SQL (or at least in postgres)
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="USER_SEQ_GEN")
    @SequenceGenerator(name="USER_SEQ_GEN", sequenceName="USER_SEQ_GEN")
    protected int ID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    protected List<Survey> surveys;
}
