package com.calc.backend.dto;

public class TheoryDto {
    private String topic;
    private String content;

    public TheoryDto() {
    }

    public TheoryDto(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
