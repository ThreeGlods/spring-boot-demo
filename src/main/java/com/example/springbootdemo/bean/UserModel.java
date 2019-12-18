package com.example.springbootdemo.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserModel {

    @Id
    private int num;

    private String name;

    private String votes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
