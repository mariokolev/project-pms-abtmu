package service;

import dto.UserDTO;
import mapper.UserMapper;
import repository.ConnectionRepository;

import java.util.HashMap;
import java.util.List;

public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<UserDTO> findAllByUserIdAndStatus(Long id, String status) {
        return UserMapper.convertEntityToDto(connectionRepository.findAllByUserId(id, status));
    }

    public UserDTO update(HashMap<String, Object> parameters, Long senderId) {
        Long requesterId = Long.parseLong(String.valueOf(parameters.get("requesterId")));
        Long recipientId = Long.parseLong(String.valueOf(parameters.get("recipientId")));
        String status = (String) parameters.get("status");
        String oldStatus = (String) parameters.get("oldStatus");

        return UserMapper.convertEntityToDto(connectionRepository.update(requesterId, recipientId, senderId, status, oldStatus));
    }

    /**
     *
     * @param userIds Holds requesterId and receiverId
     * @return User with receiverId
     */
    public UserDTO save(HashMap<String, Object> userIds) {
        Long requesterId = Long.parseLong(String.valueOf(userIds.get("requesterId")));
        Long recipientId = Long.parseLong(String.valueOf(userIds.get("recipientId")));
        return UserMapper.convertEntityToDto(connectionRepository.save(requesterId, recipientId));
    }
}
