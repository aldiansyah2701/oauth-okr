package oauth.okr.aldi.oauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import oauth.okr.aldi.oauth.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
	UserEntity findByEmail(String email);

}
