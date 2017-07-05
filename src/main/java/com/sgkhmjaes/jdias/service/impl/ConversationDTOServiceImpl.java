
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.ConversationDTOService;
import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.service.UserService;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import com.sgkhmjaes.jdias.service.dto.ConversationDTO;
import com.sgkhmjaes.jdias.service.dto.MessageDTO;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

@Service
@Transactional
public class ConversationDTOServiceImpl implements ConversationDTOService{
    
    private final Logger log = LoggerFactory.getLogger(ConversationDTOServiceImpl.class);
    private final AvatarDTOServiceImpl avatarDTOServiceImpl;
    private final ConversationService conversationService;
    private final UserService userService;

    public ConversationDTOServiceImpl(ConversationService conversationService, UserService userService, 
            AvatarDTOServiceImpl avatarDTOServiceImpl) {
        this.avatarDTOServiceImpl= avatarDTOServiceImpl;
        this.conversationService = conversationService;
        this.userService = userService;
    }
    
    @Override
    public ConversationDTO save(ConversationDTO conversationDTO) {
        log.debug("Request to save ConversationDTO: " + conversationDTO);
        Conversation conversation = createConversationFromConversationDTO(conversationDTO, userService.getCurrentPerson());
        Conversation saveConversation = conversationService.save(conversation);
        return createConversationDTOfromConversation(saveConversation);
    }
        
    @Override
    public List<ConversationDTO> findAll() {
        log.debug("Request to get all ConversationDTOs");
        ArrayList <ConversationDTO> conversationDTOs = new ArrayList <>();
        for (Conversation conversation : conversationService.findAll()) {
            conversationDTOs.add(createConversationDTOfromConversation(conversation));
        }
        return conversationDTOs;
    }
    
    @Override
    public ConversationDTO findOne(Long id) {
        log.debug("Request to get ConversationDTO : {}", id);
        return createConversationDTOfromConversation(conversationService.findOne(id));
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConversationDTO : {}", id);
        conversationService.delete(id);
    }
    
    @Override
    public List<ConversationDTO> search(String query) {
        log.debug("Request to search ConversationDTOs for query {}", query);
        ArrayList <ConversationDTO> conversationDTOs = new ArrayList <>();
        for (Conversation conversation : conversationService.search(query)) {
            conversationDTOs.add(createConversationDTOfromConversation(conversation));
        }
        return conversationDTOs;
    }
    
    
    private Conversation createConversationFromConversationDTO(ConversationDTO conversationDTO, Person currentPerson) {
        Conversation conversation = conversationService.findOne(conversationDTO.getId());
        if (conversation == null) conversation = new Conversation(conversationDTO.getAuthor());
        try {
            conversationDTO.mappingFromDTO(conversation);
            for (MessageDTO messageDTO : conversationDTO.getMessagesDTO()) {
                Message message = new Message();
                messageDTO.mappingFromDTO(message);
                //add new messages only
                if (messageDTO.getId()==null) {
                    conversation.addMessages(new Message(currentPerson, conversation, message));
                }
            }
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(ConversationDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conversation;
    }
    
    private ConversationDTO createConversationDTOfromConversation (Conversation conversation){
        ConversationDTO conversationDTO = new ConversationDTO ();
        HashMap <String, AuthorDTO> authorsDTO = new HashMap <> (conversation.getParticipants().size());
        try {
            for (Person participants : conversation.getParticipants()) {
                AuthorDTO authorDto = new AuthorDTO();
                AvatarDTO avatarDTO = avatarDTOServiceImpl.findOne(participants.getId());
                authorDto.mappingToDTO(participants, avatarDTO);
                authorsDTO.put(authorDto.getDiasporaId(), authorDto);
            }
            List <MessageDTO> messagesDTO = new ArrayList <>(conversation.getMessages().size());
            MessageDTO messageDTO = new MessageDTO();
            for (Message message : conversation.getMessages()) {
                messageDTO.mappingToDTO(message, authorsDTO.get(message.getAuthor()));
                messagesDTO.add(messageDTO);
            }
            conversationDTO.mappingToDTO(conversation);            
            conversationDTO.setAuthorDTO(new HashSet(authorsDTO.values()));
            conversationDTO.setMessageDTO(messagesDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(ConversationDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conversationDTO;
    }
        
}
