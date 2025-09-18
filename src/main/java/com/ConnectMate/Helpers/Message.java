package com.ConnectMate.Helpers;

public class Message {
    private String content;
    private MessageType type = MessageType.blue;

    public Message() {
    }

    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public static class Builder {
        private String content;
        private MessageType type = MessageType.blue;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public Message build() {
            return new Message(content, type);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
