package mapper;

import dto.ConversationDTO;
import dto.UserDTO;
import entity.Conversation;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO convertEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

    public static User convertDtoToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        return user;
    }

    public static List<UserDTO> convertEntityToDto(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(convertEntityToDto(user));
        }

        return userDTOs;
    }
}
