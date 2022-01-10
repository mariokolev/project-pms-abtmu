package my.android.client.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.android.client.R;
import my.android.client.adapter.ChatAdapter;
import my.android.client.viewmodel.ConversationViewModel;
import my.android.client.viewmodel.MessageViewModel;

public class ChatActivity extends BaseActivity{

    private RecyclerView recyclerView;
    private Button btnSend;
    private EditText messageToSend;
    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Long conversationId = getIntent().getExtras().getLong("conversationId");
        messageViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MessageViewModel.class);
        messageViewModel.setConversationId(conversationId);

        btnSend = findViewById(R.id.btnSend);
        messageToSend = findViewById(R.id.messageToSend);

        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ChatAdapter adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
    }
}