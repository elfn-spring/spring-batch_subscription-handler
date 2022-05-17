package com.subscription.handler.subscriptionHandler.dtos;

import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class ClientDTO {
  private String firstName;
  private String lastName;
  private String email;
  private Date expirationDate;

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
}
