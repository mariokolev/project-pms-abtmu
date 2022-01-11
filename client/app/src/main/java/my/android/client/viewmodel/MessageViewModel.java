package my.android.client.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import my.android.client.model.Message;
import my.android.client.repository.MessageRepository;

public class MessageViewModel extends ViewModel {

    private final MessageRepository messageRepository = new MessageRepository();
    private MutableLiveData<List<Message>> messages;
    private Context context;
    private Long conversationId;
    private Long receiverId;
    private Message message;

    public MutableLiveData<List<Message>> getMessages() throws ExecutionException, InterruptedException {
        if (messages == null) {
            messages = new MutableLiveData<>();
            findAll();
        }

        return messages;
    }

    private void findAll() throws ExecutionException, InterruptedException {

//        MessageTask messageTask = new MessageTask();
//        messageTask.execute();
//        messages.setValue(messageTask.get());
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void sendMessage(String messageBody) throws JSONException, ExecutionException, InterruptedException {
//        message = new Message();
//        message.setSenderId(AuthenticationUtils.getUserId(context));
//        message.setConversationId(conversationId);
//        message.setBody(messageBody);
//        SendMessageTask sendMessageTask = new SendMessageTask();
//        sendMessageTask.execute();
//        MessageTask messageTask = new MessageTask();
//        messageTask.execute().get();
    }

//    public String testSendMessage(String messageBody) {
//        Message message = new Message();
//        message.setSenderId(AuthenticationUtils.getUserId(context));
//        message.setConversationId(conversationId);
//        message.setBody(messageBody);
//        return  message;
//    }

//    public Message getMessage() {
//        return message;
//    }
//
//    private class MessageTask extends AsyncTask<String, String, List<Message>> {
//
//        @Override
//        protected List<Message> doInBackground(String... message) {
//            List<Message> messages = new ArrayList<>();
//            try {
//                messages = messageRepository.findAllByConversationId(AuthenticationUtils.getUserId(context), receiverId, conversationId);
//
//            } catch (JSONException e) {
//                Log.e("Get messages ", e.getMessage(), e);
//            }
//            return messages;
//        }
//    }
//
//    private class SendMessageTask extends AsyncTask<String, String, Message> {
//
//        @Override
//        protected Message doInBackground(String... message) {
//            try {
//                messageRepository.save(getMessage(), receiverId);
//            } catch (JSONException e) {
//                Log.e("Send message ", e.getMessage(), e);
//            }
//            return null;
//        }
//    }
}
