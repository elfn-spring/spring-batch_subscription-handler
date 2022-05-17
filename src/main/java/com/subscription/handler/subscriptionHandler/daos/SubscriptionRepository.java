package com.subscription.handler.subscriptionHandler.daos;

import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.data.repository.CrudRepository;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
