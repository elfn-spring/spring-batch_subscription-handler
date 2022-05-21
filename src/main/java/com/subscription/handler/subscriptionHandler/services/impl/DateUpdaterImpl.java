package com.subscription.handler.subscriptionHandler.services.impl;

import com.subscription.handler.subscriptionHandler.services.DateUpdater;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 21/05/2022
 */
@Service
public class DateUpdaterImpl implements DateUpdater {
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
