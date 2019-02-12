package com.invillia.acme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.invillia.acme.model.entity.Order;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public interface OrderRepository extends CrudRepository<Order, Long>,  QueryByExampleExecutor<Order>{

}
