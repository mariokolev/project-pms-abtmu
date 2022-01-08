package mapper;

import dto.UserDTO;
import entity.User;

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
}
