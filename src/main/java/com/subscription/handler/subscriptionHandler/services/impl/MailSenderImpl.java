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
public class MailSenderImpl implements MailSender {

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
  public void send(EmailEntity emailEntity, Subscription subscription) throws MessagingException, IOException, ParseException {



    //Encapsuler le contenu du mail dans un mimeMessage
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    //Configurer l'envoi du mail avec un mimeMessageHelper
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    if(subscription.getStatus() == (State.ALMOST_EXPIRED).name()) {
      Client client = clientRepository.findBySubscriptionId(subscription.getId());
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




    }

    if(subscription.getStatus() == (State.EXPIRED).name()) {

      Client client = clientRepository.findBySubscriptionId(subscription.getId());
      String mailSubject = client.getFirstName() + " " + client.getLastName() + ", votre alerte d'expiration d'abonnement'";
      String mailContent = "<p><b> Sender name: </b> " + client.getFirstName() + " " + client.getLastName() + "</p>";
      mailContent += "<p><b> Sender email: </b> " + fromAddr + "</p>";
      mailContent += "<p><b> Subject: </b> " + mailSubject + "</p>";
      mailContent += "<p><b> Content: </b> " + "L'abonnement " + subscription.getName() + " (" + subscription.getSubscriptionType() + ") vient d'expirer et a été renouvelé jusqu'au " + format.format(subscription.getExpirationDate()) + "</p>";

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

      // Generation pdf
     new PDFGeneratorTask().generatePDF(subscription);

      FileSystemResource fileSystemResource = new FileSystemResource(new File(PDFGeneratorTask.path));

      mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

      subscription.setStatus(State.VALID.toString());

    }

    this.emailRepository.save(emailEntity);

    // Call the thread to send mail
    this.executor.setCorePoolSize(5);
    this.executor.setMaximumPoolSize(10);

    //Here we are sending mail quickly with multi-threading
    this.executor.execute(new MailSenderTask(javaMailSender,mimeMessage));



    System.out.println("Mail with attachment sent successfully. ");

  }

}
