package com.subscription.handler.subscriptionHandler.dtos;

import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.State;
import com.subscription.handler.subscriptionHandler.entities.Type;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class SubscriptionDTO {
  private String name;
  private Type subscriptionType;
  private State status;
  private Date subscriptionDate;
  private Date expirationDate;
  private Set<Client> clients = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public Set<Client> getClients() {
    return clients;
  }

  public void setClients(Set<Client> clients) {
    this.clients = clients;
  }
}
