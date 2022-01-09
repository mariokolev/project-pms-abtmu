package service;

import dto.ConversationDTO;
import entity.Conversation;
import exception.BadRequestException;
import mapper.ConversationMapper;
import repository.ConversationRepository;

import java.util.List;

public class ConversationService{

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<ConversationDTO> findAllByUserId(Long id) {
        List<Conversation> conversations = null;

        try {
            conversations = conversationRepository.findAllByUserId(id);
        } catch (BadRequestException e) {
            throw e;
        }

        return ConversationMapper.convertEntityToDto(conversations);
    }

    public Conversation find(Long id) {
        return conversationRepository.find(id);
    }
}
