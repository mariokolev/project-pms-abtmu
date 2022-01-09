package dto;


import java.util.HashMap;
import java.util.List;

public class ConversationDTO {
    private Long id;
    private List<UserDTO> users;

    public ConversationDTO() {}

    public ConversationDTO(HashMap<String, Object> parameters) {
//        users = JSONStringer.valueToString(parameters.get("users"));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
