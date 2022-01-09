package service;

import dto.MessageDTO;
import entity.Conversation;
import entity.Message;
import entity.User;
import mapper.MessageMapper;
import repository.MessageRepository;

import java.util.List;

public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ConversationService conversationService;

    public MessageService(MessageRepository messageRepository, UserService userService, ConversationService conversationService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.conversationService = conversationService;
    }

    public List<MessageDTO> findAllByConversationId(Long id) {
        return MessageMapper.convertEntityToDTOs(messageRepository.findAllByConversationId(id));
    }

    public MessageDTO save(MessageDTO messageDTO) {
        User user = userService.find(messageDTO.getSenderId());
        Conversation conversation = conversationService.find(messageDTO.getConversationId());
        Message message = MessageMapper.convertDtoToEntity(messageDTO);

        message.setSender(user);
        message.setConversation(conversation);

        return MessageMapper.convertEntityToDto(messageRepository.save(message));
    }

    public MessageDTO update(MessageDTO messageDTO) {
        User user = userService.find(messageDTO.getSenderId());
        Conversation conversation = conversationService.find(messageDTO.getConversationId());
        Message message = MessageMapper.convertDtoToEntity(messageDTO);

        message.setSender(user);
        message.setConversation(conversation);

        return MessageMapper.convertEntityToDto(messageRepository.update(message));
    }
}
