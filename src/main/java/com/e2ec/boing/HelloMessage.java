package com.e2ec.boing;

public class HelloMessage {

    private String name;
    private String groupCode; // New field for group code
    private String content; // New field for message content

    public HelloMessage() {
    }

    public HelloMessage(String name, String groupCode, String content) {
        this.name = name;
        this.groupCode = groupCode;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
