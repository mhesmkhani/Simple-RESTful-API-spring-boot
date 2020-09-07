package com.module.model;

import javax.persistence.*;

/**
 * Created by mhesmkhani on 8/24/2020.
 */
@Entity
@Table(name = "todos")
public class Todos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "discareption")
    private String discareption;


    @Column(name = "isdone")
    private String isdone;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscareption() {
        return discareption;
    }

    public void setDiscareption(String discareption) {
        this.discareption = discareption;
    }

    public String getIsdone() {
        return isdone;
    }

    public void setIsdone(String isdone) {
        this.isdone = isdone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
