package com.maice.model.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table( schema = "MAICE")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordhash;
    
    private String activationhash;

    protected User() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

	public User(String username, String passwordhash, String activationhash) {
		super();
		this.username = username;
		this.passwordhash = passwordhash;
		this.activationhash = activationhash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordhash() {
		return passwordhash;
	}

	public void setPasswordhash(String passwordhash) {
		this.passwordhash = passwordhash;
	}

	public String getActivationhash() {
		return activationhash;
	}

	public void setActivationhash(String activationhash) {
		this.activationhash = activationhash;
	}
}