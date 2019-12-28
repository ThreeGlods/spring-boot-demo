package com.example.springbootdemo.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserModel {

    @Id
    private int id;

    private String name;

    private String votes;

    private long discrepancy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVotes() {
        return votes;
    }


    public void setVotes(String votes) {
        this.votes = votes;
    }

    public long getDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(long discrepancy) {
        this.discrepancy = discrepancy;
    }
}
