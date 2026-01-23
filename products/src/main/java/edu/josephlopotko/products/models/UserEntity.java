package edu.josephlopotko.products.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String password;
    
    @Column
    private String role;

    @Column
    private boolean enabled;

    public UserEntity(int id, String username, String password, String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }


}
