package com.subscription.handler.subscriptionHandler.services.utils;

import com.lowagie.text.Document;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.Subscription;

import java.io.IOException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public abstract class AbstractTask implements Runnable{
  @Override
  public void run() {
    try{
      this.send();
      this.generatePDF(new Subscription());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public abstract void send();
  public abstract void generatePDF(Subscription subscription) throws IOException;
}
