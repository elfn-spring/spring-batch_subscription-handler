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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
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


}
