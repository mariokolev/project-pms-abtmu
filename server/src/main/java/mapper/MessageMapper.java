package mapper;

import dto.MessageDTO;
import entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public static MessageDTO convertEntityToDto(Message message) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId(message.getId());
        messageDTO.setConversationId(message.getConversation().getId());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setMessageBody(message.getMessageBody());
        messageDTO.setMedia(message.getMedia());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setUpdatedAt(message.getUpdatedAt());

        return messageDTO;
    }

    public static List<MessageDTO> convertEntityToDTOs(List<Message> messages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();

            messageDTO.setId(message.getId());
            messageDTO.setConversationId(message.getConversation().getId());
            messageDTO.setSenderId(message.getSender().getId());
            messageDTO.setMessageBody(message.getMessageBody());
            messageDTO.setMedia(message.getMedia());
            messageDTO.setCreatedAt(message.getCreatedAt());
            messageDTO.setUpdatedAt(message.getUpdatedAt());

            messageDTOs.add(messageDTO);
        }

        return messageDTOs;
    }

    public static Message convertDtoToEntity(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessageBody(messageDTO.getMessageBody());
        message.setMedia(messageDTO.getMedia());
        return message;
    }
}
