package quickndirty.minisurveymonkey;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="user", path="user")
public interface UserRepository extends JpaRepository<User, Integer> {

    // find by externalID
    User findByExternalID(@Param("externalID") String externalID);
}

