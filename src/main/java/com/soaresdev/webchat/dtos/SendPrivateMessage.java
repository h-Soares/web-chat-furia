package com.soaresdev.webchat.dtos;

public record SendPrivateMessage(String to, String from, String content) {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendPrivateMessage{");
        sb.append("content='").append(content).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append('}');
        return sb.toString();
    }
}