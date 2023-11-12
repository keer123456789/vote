package com.keer.vote.entity;

public class VoteInfoLabel {
    private String username;
    private int num;

    public VoteInfoLabel(String username, int num) {
        this.username = username;
        this.num = num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public VoteInfoLabel() {
    }

    public void add(int num) {
        this.num = this.num + num;
        if (this.num < 0) {
            this.num = 0;
        }
    }
}
