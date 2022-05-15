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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Type subscriptionType;
  private State status;
  private Date subscriptionDate;
  private Date expirationDate;

  @OneToMany(mappedBy = "subscription",cascade = CascadeType.ALL)
  private Set<Client> products = new HashSet<>();
}
