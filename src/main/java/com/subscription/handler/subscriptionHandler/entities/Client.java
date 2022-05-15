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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
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
}
