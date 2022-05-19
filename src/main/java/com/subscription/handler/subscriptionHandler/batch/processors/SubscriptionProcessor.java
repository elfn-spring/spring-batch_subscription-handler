package com.subscription.handler.subscriptionHandler.batch.processors;

import com.subscription.handler.subscriptionHandler.entities.State;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 19/05/2022
 */
public class SubscriptionProcessor implements ItemProcessor<Subscription, Subscription> {
  @Override
  public Subscription process(Subscription subscription) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    String date1 = format.format(subscription.getExpirationDate());
    String date2 = format.format(new Date());

    java.sql.Date sqlDate1 = java.sql.Date.valueOf(date1);
    java.sql.Date sqlDate2 = java.sql.Date.valueOf(date2);
    LocalDate ld1 = sqlDate1.toLocalDate();
    LocalDate ld2 = sqlDate2.toLocalDate();

    long daysBetween = ChronoUnit.DAYS.between(ld1, ld2);


    boolean isExpired = ((date1.compareTo(date2) == 0)) ? true : false;
    boolean isAlmostExpired = ((daysBetween == 5)) ? true : false;

    if(isExpired){
      subscription.setStatus((State.EXPIRED).toString());
      return subscription;
    }

    if(isAlmostExpired){
      subscription.setStatus((State.ALMOST_EXPIRED).toString());
      return subscription;
    }
    return null;
  }
}