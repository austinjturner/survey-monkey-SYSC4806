package quickndirty.minisurveymonkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer>{

    //find by Name
    public Survey findByName(String name);

}
