package dto;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.time.LocalDateTime;
import java.util.HashMap;

public class MessageDTO {
    private Long id;
    private Long senderId;
    private String messageBody;
    private Boolean isMedia;
    private Long conversationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MessageDTO() {}

    public MessageDTO(HashMap<String, Object> parameters) {
        senderId = Long.parseLong(JSONStringer.valueToString(parameters.get("sender_id")));
        messageBody = JSONStringer.valueToString(parameters.get("body"));
        isMedia = (Boolean) parameters.get("is_media");
        conversationId = Long.parseLong(JSONStringer.valueToString(parameters.get("conversation_id")));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Boolean getMedia() {
        return isMedia;
    }

    public void setMedia(Boolean media) {
        isMedia = media;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
