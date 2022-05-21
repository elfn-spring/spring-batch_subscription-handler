package com.subscription.handler.subscriptionHandler.services.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class MailSenderTask extends AbstractTask{

  final private JavaMailSender javaMailSender;
  private MimeMessage mimeMessage;

  public MailSenderTask(JavaMailSender javaMailSender, MimeMessage mimeMessage) {
    this.javaMailSender = javaMailSender;
    this.mimeMessage = mimeMessage;
  }

  @Override
  public void send() {
    if(this.mimeMessage != null)
    {
      javaMailSender.send(this.mimeMessage);
    }
  }

  @Override
  public void generatePDF(Subscription subscription) throws IOException {

  }




}
