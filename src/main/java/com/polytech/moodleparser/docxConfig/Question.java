package com.polytech.moodleparser.docxConfig;

import java.util.List;

public class Question {
    private String text = "";
    private String type = "";
    private List<String> answers = null;

    public Question() {
    }

    public Question(String text, String type, List<String> answers) {
        this.text = text;
        this.type = type;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", answers=" + answers +
                '}';
    }
}
