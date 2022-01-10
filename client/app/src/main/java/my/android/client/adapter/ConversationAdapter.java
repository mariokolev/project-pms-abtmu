package my.android.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.android.client.R;
import my.android.client.activity.ChatActivity;
import my.android.client.model.Conversation;
import my.android.client.model.User;
import my.android.client.util.AuthenticationUtils;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationHolder> {

    private List<Conversation> conversations = new ArrayList<>();
    private Context context;

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
        String username = "";
        for (User user : currentConversation.getUsers()) {
            if (!user.getId().equals(AuthenticationUtils.getUserId(context))) {
                username = user.getUsername();
            }
        }
        holder.conversationName.setText(username);

        holder.getCardView().setOnClickListener(view -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("conversationId", currentConversation.getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    static class ConversationHolder extends RecyclerView.ViewHolder {
        private TextView conversationName;
        private CardView cardView;

        public ConversationHolder(@NonNull View itemView) {
            super(itemView);
            conversationName = itemView.findViewById(R.id.txtConversationName);
            cardView = itemView.findViewById(R.id.conversationCardView);
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
