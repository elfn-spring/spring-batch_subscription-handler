package com.subscription.handler.subscriptionHandler.services;

import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import com.subscription.handler.subscriptionHandler.entities.Subscription;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public interface MailSender {
  public void send(EmailEntity emailEntity, Subscription subscription) throws MessagingException, UnsupportedEncodingException;
}
