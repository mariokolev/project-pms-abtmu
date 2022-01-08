package my.android.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.android.client.R;
import my.android.client.model.Conversation;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationHolder> {

    private List<Conversation> conversations = new ArrayList<>();

    @NonNull
    @Override
    public ConversationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_item, parent, false);
        return new ConversationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationHolder holder, int position) {
        Conversation currentConversation = conversations.get(position);
        holder.conversationName.setText(currentConversation.getUsers().get(0).getUsername());
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }


    class ConversationHolder extends RecyclerView.ViewHolder {
        private TextView conversationName;

        public ConversationHolder(@NonNull View itemView) {
            super(itemView);
            conversationName = itemView.findViewById(R.id.txtConversationName);
        }
    }
}
