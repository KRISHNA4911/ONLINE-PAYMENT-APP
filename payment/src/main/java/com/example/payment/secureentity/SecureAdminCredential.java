package com.example.payment.secureentity;

import jakarta.persistence.*;

@Entity
@Table(name = "secure_admin_credentials")
public class SecureAdminCredential {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
