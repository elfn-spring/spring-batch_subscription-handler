package com.subscription.handler.subscriptionHandler.services;

import java.text.ParseException;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 19/05/2022
 */
public interface DateUpdater {
  public Date getNewDate(Date oldDate) throws ParseException;
}
