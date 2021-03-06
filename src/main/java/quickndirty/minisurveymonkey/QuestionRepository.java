package quickndirty.minisurveymonkey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="question", path="question")
public interface QuestionRepository extends JpaRepository<Question, Integer> {


}