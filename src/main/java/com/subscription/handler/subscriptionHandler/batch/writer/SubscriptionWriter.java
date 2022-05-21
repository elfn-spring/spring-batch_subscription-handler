package com.subscription.handler.subscriptionHandler.batch.writer;

import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import com.subscription.handler.subscriptionHandler.services.MailSender;
import com.subscription.handler.subscriptionHandler.services.utils.PDFGeneratorTask;
import org.springframework.batch.item.ItemWriter;


import java.io.File;
import java.util.List;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 20/05/2022
 */
public class SubscriptionWriter implements ItemWriter<Subscription> {

  final  private MailSender mailSender;

  public SubscriptionWriter(MailSender mailSender) {
    this.mailSender = mailSender;
  }


  @Override
  public void write(List<? extends Subscription> subscriptions) throws Exception {
    //Pour chaque produit on va generer le contenu du mail
    for(Subscription  subscription : subscriptions)
    {
      EmailEntity emailEntity = new EmailEntity();
      this.mailSender.send(emailEntity,subscription);
    }
    emptyDirectory();
  }
  public void emptyDirectory(){
    File folder = new File("System.getProperty(\"user.dir\")+\"/pdf/");

    for (File file: folder.listFiles()) {
      file.delete();
    }

  }

}
