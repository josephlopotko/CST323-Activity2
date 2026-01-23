package edu.josephlopotko.products.data;

import org.springframework.data.repository.CrudRepository;
import edu.josephlopotko.products.models.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Integer>{

}
