package com.subscription.handler.subscriptionHandler.services.utils;

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
}
