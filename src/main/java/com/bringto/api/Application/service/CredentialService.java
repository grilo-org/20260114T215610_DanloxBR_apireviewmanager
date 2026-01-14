package com.bringto.api.Application.config.service;

import com.bringto.api.Application.config.model.User;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
public class CredentialService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String login;

    @Column(nullable = false)
    private String encryptedPassword;

    private String url;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
