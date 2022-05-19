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
@Table(name = "client")
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private Date expirationDate;

  @ManyToOne
  private Subscription subscription;

  @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
  private Set<EmailEntity> mails = new HashSet<>();

  public Client(Long id, String firstName, String lastName, String email, Date expirationDate, Subscription subscription, Set<EmailEntity> mails) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.expirationDate = expirationDate;
    this.subscription = subscription;
    this.mails = mails;
  }

  public Client() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public Subscription getSubscription() {
    return subscription;
  }

  public void setSubscription(Subscription subscription) {
    this.subscription = subscription;
  }

  public Set<EmailEntity> getMails() {
    return mails;
  }

  public void setMails(Set<EmailEntity> mails) {
    this.mails = mails;
  }
}
