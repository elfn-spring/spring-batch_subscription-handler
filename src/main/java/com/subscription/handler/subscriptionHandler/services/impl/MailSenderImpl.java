package com.subscription.handler.subscriptionHandler.services.impl;

import com.lowagie.text.Document;
import com.subscription.handler.subscriptionHandler.daos.ClientRepository;
import com.subscription.handler.subscriptionHandler.daos.EmailEntityRepository;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.dtos.mappers.SubscriptionMapper;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import com.subscription.handler.subscriptionHandler.entities.State;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import com.subscription.handler.subscriptionHandler.services.DateUpdater;
import com.subscription.handler.subscriptionHandler.services.MailSender;
import com.subscription.handler.subscriptionHandler.services.utils.MailSenderTask;
import com.subscription.handler.subscriptionHandler.services.utils.PDFGeneratorTask;
import freemarker.template.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Service
public class MailSenderImpl implements MailSender, DateUpdater {

  private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);


  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private EmailEntityRepository emailRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Value("${spring.mail.username}")
  private String mailexpeditor;

  private PDFGeneratorTask pdfGeneratorTask = new PDFGeneratorTask();

  @Autowired
  private SubscriptionMapper subscriptionMapper;


  @Value("${spring.mail.username}")
  private String fromAddr;


  @Override
  public void send(EmailEntity emailEntity, Subscription subscription, HttpServletResponse response) throws MessagingException, IOException, ParseException {




    // Generation pdf
    Document document = pdfGeneratorTask.generatePDF(subscription);

    //Encapsuler le contenu du mail dans un mimeMessage
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    //Configurer l'envoi du mail avec un mimeMessageHelper
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


    if(subscription.getStatus() == "ALMOST_EXPIRED") {
      Client client = subscription.getClients().stream().filter(c -> c.getSubscription().getName().equalsIgnoreCase(subscription.getName())).findAny().get();
      String mailSubject = client.getFirstName() + " " + client.getLastName() + ", votre alerte d'expiration de produit";
      String mailContent = "<p><b> Sender name: </b> " + client.getFirstName() + " " + client.getLastName() + "</p>";
      mailContent += "<p><b> Sender email: </b> " + fromAddr + "</p>";
      mailContent += "<p><b> Subject: </b> " + mailSubject + "</p>";
      mailContent += "<p><b> Content: </b> " + "L'abonnement " + subscription.getName() + " (" + subscription.getSubscriptionType() + ") expire dans 5 jours" + "</p>";

      emailEntity.setSubject(mailSubject);
      emailEntity.setFrom(fromAddr);
      emailEntity.setTo(client.getEmail());
      emailEntity.setContent(mailContent);
      emailEntity.setSendingDate(new Date());
      emailEntity.setClient(client);

      mimeMessageHelper.setTo(emailEntity.getTo());
      mimeMessageHelper.setSubject(emailEntity.getSubject());
      mimeMessageHelper.setText(emailEntity.getContent());
      mimeMessageHelper.setFrom(emailEntity.getFrom(),"My");
      mimeMessageHelper.setText(emailEntity.getContent(), true);

      FileSystemResource fileSystemResource = new FileSystemResource(new File(PDFGeneratorTask.path));

      mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);


    }

    if(subscription.getStatus() == "EXPIRED") {

      Client client = subscription.getClients().stream().filter(c -> c.getSubscription().getName().equalsIgnoreCase(subscription.getName())).findAny().get();
      String mailSubject = client.getFirstName() + " " + client.getLastName() + ", votre alerte d'expiration d'abonnement'";
      String mailContent = "<p><b> Sender name: </b> " + client.getFirstName() + " " + client.getLastName() + "</p>";
      mailContent += "<p><b> Sender email: </b> " + fromAddr + "</p>";
      mailContent += "<p><b> Subject: </b> " + mailSubject + "</p>";
      mailContent += "<p><b> Content: </b> " + "L'abonnement " + subscription.getName() + " (" + subscription.getSubscriptionType() + ") vient d'expirer et a été renouvelé jusqu'au " +getNewDate(subscription.getExpirationDate())+ "</p>";

      emailEntity.setSubject(mailSubject);
      emailEntity.setFrom(fromAddr);
      emailEntity.setTo(client.getEmail());
      emailEntity.setContent(mailContent);
      emailEntity.setSendingDate(new Date());
      emailEntity.setClient(client);

      mimeMessageHelper.setTo(emailEntity.getTo());
      mimeMessageHelper.setSubject(emailEntity.getSubject());
      mimeMessageHelper.setText(emailEntity.getContent());
      mimeMessageHelper.setFrom(emailEntity.getFrom(),"My");
      mimeMessageHelper.setText(emailEntity.getContent(), true);

    }

    this.emailRepository.save(emailEntity);

    // Call the thread to send mail
    this.executor.setCorePoolSize(5);
    this.executor.setMaximumPoolSize(10);

    //Here we are sending mail quickly with multi-threading
    this.executor.execute(new MailSenderTask(javaMailSender,mimeMessage));

    System.out.println("Mail with attachment sent successfully. ");

  }

  @Override
  public Date getNewDate(Date oldDate) throws ParseException {
    //New Date after added one month
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String strOldDate = format.format(oldDate);
    java.sql.Date old = java.sql.Date.valueOf(strOldDate);
    LocalDate localOldDate = old.toLocalDate();

    LocalDate newLocalDate = localOldDate.plusMonths(1);

    Date newDate = format.parse(newLocalDate.toString());
    return newDate;
  }
}
