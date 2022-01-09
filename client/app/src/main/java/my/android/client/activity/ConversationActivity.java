package my.android.client.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import my.android.client.R;
import my.android.client.adapter.ConversationAdapter;
import my.android.client.viewmodel.ConversationViewModel;

public class ConversationActivity extends BaseActivity {

    private ConversationViewModel conversationViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        recyclerView = findViewById(R.id.conversationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ConversationAdapter adapter = new ConversationAdapter();
        recyclerView.setAdapter(adapter);

        conversationViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ConversationViewModel.class);
        conversationViewModel.setContext(getApplicationContext());
        try {
            conversationViewModel.findAllConversations().observe(this,
                    adapter::setConversations);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}