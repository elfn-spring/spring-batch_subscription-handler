package com.subscription.handler.subscriptionHandler.dtos.mappers;

import com.subscription.handler.subscriptionHandler.dtos.ClientDTO;
import com.subscription.handler.subscriptionHandler.entities.Client;
import org.mapstruct.Mapper;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {
  ClientDTO toClientDTO(Client client);
  Client toClient(ClientDTO clientDTO);
}
