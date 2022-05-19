package com.subscription.handler.subscriptionHandler.dtos.mappers;

import com.subscription.handler.subscriptionHandler.dtos.ClientDTO;
import com.subscription.handler.subscriptionHandler.dtos.EmailEntityDTO;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.EmailEntity;
import org.mapstruct.Mapper;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
@Mapper(componentModel = "spring")
public interface EmailEntityMapper {
  EmailEntityDTO toEmailDTO(EmailEntity emailEntity);
  EmailEntity toEmail(EmailEntityDTO emailEntityDTO);
}
