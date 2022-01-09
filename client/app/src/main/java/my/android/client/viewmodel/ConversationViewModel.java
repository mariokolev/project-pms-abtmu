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
import my.android.client.repository.ConversationRepository;
import my.android.client.util.AuthenticationUtils;

public class ConversationViewModel extends ViewModel {

    private final ConversationRepository conversationRepository = new ConversationRepository();
    private MutableLiveData<List<Conversation>> conversations;
    private Context context;

    public MutableLiveData<List<Conversation>> findAllConversations() throws ExecutionException, InterruptedException {
        if (conversations == null) {
            conversations = new MutableLiveData<>();
            findAll();
        }

        return conversations;
    }

    private void findAll() throws ExecutionException, InterruptedException {

        ConversationTask conversationTask = new ConversationTask();
        List<Conversation> conversationList = conversationTask.execute().get();
            conversations.setValue(conversationList);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private class ConversationTask extends AsyncTask<String, String, List<Conversation>> {

        @Override
        protected List<Conversation> doInBackground(String... message) {
            List<Conversation> conversations = new ArrayList<>();
            try {
                conversations = conversationRepository.findAllByUserId(AuthenticationUtils.getUserId(context));
            } catch (JSONException e) {
                Log.e("Get conversations ", e.getMessage(), e);
            }
            return conversations;
        }
    }
}
