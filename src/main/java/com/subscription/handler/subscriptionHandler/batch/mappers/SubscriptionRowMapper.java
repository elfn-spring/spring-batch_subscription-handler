package com.subscription.handler.subscriptionHandler.batch.mappers;

import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 19/05/2022
 */

public class SubscriptionRowMapper implements RowMapper<Subscription> {
  @Override
  public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
    Subscription subscription = new Subscription();
    Client client = new Client();

    subscription.setId(rs.getLong("id"));
    subscription.getClients().add(client);
    subscription.setStatus(rs.getString("status"));
    subscription.setSubscriptionType(rs.getString("subscription_type"));
    subscription.setExpirationDate(rs.getDate("expiration_date"));
    subscription.setSubscriptionDate(rs.getDate("subscription_date"));
    subscription.setName(rs.getString("name"));

    return subscription;
  }
}
