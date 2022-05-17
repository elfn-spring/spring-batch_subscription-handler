package com.subscription.handler.subscriptionHandler.dtos.mappers;

import com.subscription.handler.subscriptionHandler.dtos.ClientDTO;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.mapstruct.Mapper;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Mapper
public interface SubscriptionMapper {
  SubscriptionDTO toSubscriptionDTO(Subscription subscription);
  Subscription toSubscription(SubscriptionDTO subscriptionDTO);
}
