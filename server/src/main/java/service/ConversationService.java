package service;

import dto.ConversationDTO;
import entity.Conversation;
import mapper.ConversationMapper;
import repository.ConversationRepository;

import java.util.List;

public class ConversationService{

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<ConversationDTO> findAllByUserId(Long id) {
        return ConversationMapper.convertEntityToDto(conversationRepository.findAllByUserId(id));
    }

    public Conversation find(Long id) {
        return conversationRepository.find(id);
    }
}
