package mapper;


import dto.ConversationDTO;
import dto.UserDTO;
import entity.Conversation;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationMapper {

    public static List<ConversationDTO> convertEntityToDto(List<Conversation> conversations) {
        List<ConversationDTO> conversationDTOs = new ArrayList<>();

        for (Conversation conversation : conversations) {
            ConversationDTO conversationDTO = new ConversationDTO();
            List<UserDTO> users = new ArrayList<>();

            conversationDTO.setId(conversation.getId());

            for (User user : conversation.getUsers()) {
                users.add(UserMapper.convertEntityToDto(user));
            }

            conversationDTO.setUsers(users);
            conversationDTOs.add(conversationDTO);
        }

        return conversationDTOs;
    }
}
