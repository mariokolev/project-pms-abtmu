package my.android.client.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import my.android.client.R;
import my.android.client.adapter.ChatAdapter;
import my.android.client.model.Message;
import my.android.client.repository.MessageRepository;
import my.android.client.util.AuthenticationUtils;
import my.android.client.viewmodel.MessageViewModel;

public class ChatActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Button btnSend;
    private EditText messageToSend;
    private MessageViewModel messageViewModel;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Long conversationId = getIntent().getExtras().getLong("conversationId");
        Long receiverId = getIntent().getExtras().getLong("receiverId");

        btnSend = findViewById(R.id.btnSend);
        messageToSend = findViewById(R.id.messageToSend);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter();
        adapter.setContext(getApplicationContext());
        recyclerView.setAdapter(adapter);

        MessageTask messageTask = new MessageTask(getApplicationContext(), receiverId, conversationId);
        try {
            messageTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        btnSend.setOnClickListener(view -> {
            SendMessageTask sendMessageTask = new SendMessageTask(getApplicationContext(), receiverId, conversationId);
            sendMessageTask.execute();
            messageToSend.setText("");
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(messageToSend.getApplicationWindowToken(),0);
        });
    }

    private class MessageTask extends AsyncTask<String, String, List<Message>> {
        private MessageRepository messageRepository = new MessageRepository();
        private Context context;
        private Long receiverId;
        private Long conversationId;

        public MessageTask(Context context, Long receiverId, Long conversationId) {
            this.context = context;
            this.receiverId = receiverId;
            this.conversationId = conversationId;
        }

        @Override
        protected List<Message> doInBackground(String... message) {
            List<Message> messages = new ArrayList<>();
            try {
                messages = messageRepository.findAllByConversationId(AuthenticationUtils.getUserId(context), receiverId, conversationId);
                adapter.setMessages(messages);

            } catch (JSONException e) {
                Log.e("Get messages ", e.getMessage(), e);
            }
            return null;
        }
    }

    private class SendMessageTask extends AsyncTask<Message, Message, Message> {
        private MessageRepository messageRepository = new MessageRepository();
        private Context context;
        private Long receiverId;
        private Long conversationId;
        private Message message;

        public SendMessageTask(Context context, Long receiverId, Long conversationId) {
            this.context = context;
            this.receiverId = receiverId;
            this.conversationId = conversationId;
        }

        @Override
        protected void onPostExecute(Message message) {
            super.onPostExecute(message);
            System.out.println("Executing on post: " + message.getBody());
            adapter.addMessage(message);
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());
        }

        @Override
        protected Message doInBackground(Message... messages) {
            try {
                message = new Message();
                message.setSenderId(AuthenticationUtils.getUserId(context));
                message.setConversationId(conversationId);
                message.setBody(messageToSend.getText().toString());

                System.out.println("Repository.save called!!");
                message = messageRepository.save(message, receiverId);
                System.out.println("SendMessageTask received: " + message.toString());


                return message;
            } catch (JSONException e) {
                Log.e("Send message ", e.getMessage(), e);
            }
            return message;
        }

        @Override
        protected void onProgressUpdate(Message... values) {
            super.onProgressUpdate(values);
            System.out.println("OnProgressUpdate: " + message.getBody());
            adapter.addMessage(message);
        }
    }
}