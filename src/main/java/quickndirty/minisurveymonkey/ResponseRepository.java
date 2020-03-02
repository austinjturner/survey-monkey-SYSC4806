package quickndirty.minisurveymonkey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel="response", path="response")
public interface ResponseRepository extends JpaRepository<Response, Integer> {


}