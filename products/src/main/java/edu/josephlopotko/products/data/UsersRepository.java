package edu.josephlopotko.products.data;

import org.springframework.data.repository.CrudRepository;
import edu.josephlopotko.products.models.UserEntity;
import java.util.List;


public interface UsersRepository extends CrudRepository<UserEntity, Integer>{

    UserEntity findByUsername(String username);
}
