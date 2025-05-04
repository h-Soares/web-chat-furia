package com.soaresdev.webchat.dtos;

public record Message(String from, String content) {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("content='").append(content).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append('}');
        return sb.toString();
    }
}