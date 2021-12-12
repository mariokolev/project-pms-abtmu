package service;

import entity.Conversation;
import repository.ConversationRepository;

import java.util.List;

public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

//    public List<Conversation> findAllByUserId(Long id) {
//
//        if (id == null || id < 0) {
//
//        }
//
//        List<Conversation> conversations = null;
//
//
//            conversations = conversationRepository.findAllByUserId(id);
//
//
//    }
//
//    public Conversation save(Conversation conversation) {
//        conversation.getUsers().size()
//    }
}
