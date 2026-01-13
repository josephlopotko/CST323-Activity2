package edu.josephlopotko.products.data;

import org.springframework.data.repository.CrudRepository;

import edu.josephlopotko.products.models.OrderEntity;

public interface OrdersRepository extends CrudRepository<OrderEntity, Integer>{

}
