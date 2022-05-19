package com.subscription.handler.subscriptionHandler.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 18/10/2021
 */
@Entity
@Table(name = "subscription")
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String subscriptionType;
  private String status;
  private Date subscriptionDate;
  private Date expirationDate;

  @OneToMany(mappedBy = "subscription",cascade = CascadeType.ALL)
  private Set<Client> clients = new HashSet<>();

  public Subscription(String name, String subscriptionType, String status, Date subscriptionDate, Date expirationDate, Set<Client> clients) {
    this.name = name;
    this.subscriptionType = subscriptionType;
    this.status = status;
    this.subscriptionDate = subscriptionDate;
    this.expirationDate = expirationDate;
    this.clients = clients;
  }

  public Subscription() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSubscriptionType() {
    return subscriptionType;
  }

  public void setSubscriptionType(String subscriptionType) {
    this.subscriptionType = subscriptionType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
