package my.android.client.adapter;

import android.content.Context;
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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private List<String> messages = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return  new ChatAdapter.ChatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        String message = messages.get(position);
        holder.messageText.setText("test");
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static class ChatHolder extends RecyclerView.ViewHolder {
        private TextView messageText;
        private CardView cardView;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            cardView = itemView.findViewById(R.id.messageCardView);
        }
    }
}
