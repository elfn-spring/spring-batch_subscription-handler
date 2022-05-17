package com.subscription.handler.subscriptionHandler.dtos;

import com.subscription.handler.subscriptionHandler.entities.State;
import com.subscription.handler.subscriptionHandler.entities.Type;

import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class SubscriptionDTO {
  private Type subscriptionType;
  private State status;
  private Date subscriptionDate;
  private Date expirationDate;

  public Type getSubscriptionType() {
    return subscriptionType;
  }

  public void setSubscriptionType(Type subscriptionType) {
    this.subscriptionType = subscriptionType;
  }

  public State getStatus() {
    return status;
  }

  public void setStatus(State status) {
    this.status = status;
  }

  public Date getSubscriptionDate() {
    return subscriptionDate;
  }

  public void setSubscriptionDate(Date subscriptionDate) {
    this.subscriptionDate = subscriptionDate;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }
}
