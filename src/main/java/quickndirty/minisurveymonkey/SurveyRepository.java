package quickndirty.minisurveymonkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="survey", path="survey")
public interface SurveyRepository extends JpaRepository<Survey, Integer>{
    //find by Name
    Survey findByName(@Param("name") String name);
}
