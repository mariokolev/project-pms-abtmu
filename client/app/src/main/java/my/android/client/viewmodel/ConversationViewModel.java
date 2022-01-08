package my.android.client.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import my.android.client.model.Conversation;
import my.android.client.repository.ConversationRepository;

public class ConversationViewModel extends ViewModel {

    private final ConversationRepository conversationRepository = new ConversationRepository();
    private MutableLiveData<List<Conversation>> conversations;

    public MutableLiveData<List<Conversation>> findAllConversations() {
        if (conversations == null) {
            conversations = new MutableLiveData<>();
            findAll();
        }

        return conversations;
    }

    private void findAll() {
            List<Conversation> conversationList = conversationRepository.findAll();
            conversations.setValue(conversationList);
    }
}
