package com.subscription.handler.subscriptionHandler.services.impl;

import com.subscription.handler.subscriptionHandler.daos.ClientRepository;
import com.subscription.handler.subscriptionHandler.daos.EmailEntityRepository;
import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import com.subscription.handler.subscriptionHandler.services.MailSender;
import com.subscription.handler.subscriptionHandler.services.utils.PDFGeneratorTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Service
public class MailSenderImpl implements MailSender {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private EmailEntityRepository emailRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Value("${spring.mail.username}")
  private String mailexpeditor;

  private PDFGeneratorTask pdfGeneratorTask = new PDFGeneratorTask();

  @Override
  public void send(EmailEntity emailEntity, Subscription subscription) throws MessagingException, UnsupportedEncodingException {

  }
}
