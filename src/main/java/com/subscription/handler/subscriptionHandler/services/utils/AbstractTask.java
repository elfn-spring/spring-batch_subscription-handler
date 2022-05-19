package com.subscription.handler.subscriptionHandler.services.utils;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
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
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public abstract void send();
  public abstract Document generatePDF(HttpServletResponse response , Subscription subscription) throws IOException;
}
