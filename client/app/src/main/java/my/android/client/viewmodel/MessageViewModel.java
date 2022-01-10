package my.android.client.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import my.android.client.model.Conversation;
import my.android.client.model.Message;
import my.android.client.repository.MessageRepository;
import my.android.client.util.AuthenticationUtils;

public class MessageViewModel extends ViewModel {

    private final MessageRepository messageRepository = new MessageRepository();
    private MutableLiveData<List<Message>> messages;
    private Context context;
    private Long conversationId;

    public MutableLiveData<List<Message>> getMessages() throws ExecutionException, InterruptedException {
        if (messages == null) {
            messages = new MutableLiveData<>();
            findAll();
        }

        return messages;
    }

    private void findAll() throws ExecutionException, InterruptedException {

        MessageTask messageTask = new MessageTask();
        List<Message> conversationList = messageTask.execute().get();
        messages.setValue(conversationList);
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    private class MessageTask extends AsyncTask<String, String, List<Message>> {

        @Override
        protected List<Message> doInBackground(String... message) {
            List<Message> messages = new ArrayList<>();
            try {
                messages = messageRepository.findAllByConversationId(AuthenticationUtils.getUserId(context), conversationId);
            } catch (JSONException e) {
                Log.e("Get messages ", e.getMessage(), e);
            }
            return messages;
        }
    }
}
