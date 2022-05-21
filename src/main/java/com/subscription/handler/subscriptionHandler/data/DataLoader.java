package com.subscription.handler.subscriptionHandler.data;

import com.subscription.handler.subscriptionHandler.daos.ClientRepository;
import com.subscription.handler.subscriptionHandler.daos.EmailEntityRepository;
import com.subscription.handler.subscriptionHandler.daos.SubscriptionRepository;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.State;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import com.subscription.handler.subscriptionHandler.entities.Type;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 19/05/2022
 */
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private final ClientRepository clientRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final EmailEntityRepository emailRepository;

  public DataLoader(ClientRepository clientRepository, SubscriptionRepository subscriptionRepository, EmailEntityRepository emailRepository) {
    this.clientRepository = clientRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.emailRepository = emailRepository;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    try {
      load();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void load() throws ParseException {
    Client client1 = new Client();
    client1.setEmail("elimanefofana17@gmail.com");
    client1.setFirstName("Elimane");
    client1.setLastName("Fofana");

    Client client2 = new Client();
    client2.setEmail("gelkov30@gmail.com");
    client2.setFirstName("John");
    client2.setLastName("Doe");

//    Set<Client> clients = new HashSet<>();
//    clients.add(client1);
//    clients.add(client2);


    Subscription canal = new Subscription("CANAL+", "BASIC", (State.VALID).name(), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-17"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-20"), new HashSet<>());
    Subscription netfix = new Subscription("NETFLIX", "MEDIUM", (State.VALID).name(), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-18"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-20"), new HashSet<>());
    Subscription pluralsight = new Subscription("PLURALSIGHT", "MEDIUM", (State.VALID.name()).toString(), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-18"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-20"), new HashSet<>());

    client1.setSubscription(canal);
    client2.setSubscription(pluralsight);

    canal.getClients().add(client1);
    pluralsight.getClients().add(client2);

    this.subscriptionRepository.save(canal);
    this.subscriptionRepository.save(pluralsight);
    this.subscriptionRepository.save(netfix);

    this.clientRepository.save(client1);
    this.clientRepository.save(client2);
  }


}
