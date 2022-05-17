package com.subscription.handler.subscriptionHandler.daos;

import com.subscription.handler.subscriptionHandler.entities.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
