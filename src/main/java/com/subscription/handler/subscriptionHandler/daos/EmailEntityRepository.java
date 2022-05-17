package com.subscription.handler.subscriptionHandler.daos;

import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Repository
public interface EmailEntityRepository extends CrudRepository<EmailEntity, Long> {
}
