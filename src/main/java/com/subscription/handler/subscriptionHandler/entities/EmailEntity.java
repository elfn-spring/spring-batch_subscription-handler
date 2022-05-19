package com.subscription.handler.subscriptionHandler.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 18/10/2021
 */
@Entity
@Table(name = "email")
public class EmailEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String subject;
  private String from;
  private String to;
  private String content;
  private Date sendingDate;

  @ManyToOne
  private Client client;

  public EmailEntity(Long id, String subject, String from, String to, String content, Date sendingDate, Client client) {
    this.id = id;
    this.subject = subject;
    this.from = from;
    this.to = to;
    this.content = content;
    this.sendingDate = sendingDate;
    this.client = client;
  }

  public EmailEntity() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getSendingDate() {
    return sendingDate;
  }

  public void setSendingDate(Date sendingDate) {
    this.sendingDate = sendingDate;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
