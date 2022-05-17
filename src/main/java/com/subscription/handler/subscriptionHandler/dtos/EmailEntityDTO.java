package com.subscription.handler.subscriptionHandler.dtos;

import com.subscription.handler.subscriptionHandler.entities.Client;

import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class EmailEntityDTO {
  private String subject;
  private String from;
  private String to;
  private String content;
  private Date sendingDate;
  private Client client;

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
