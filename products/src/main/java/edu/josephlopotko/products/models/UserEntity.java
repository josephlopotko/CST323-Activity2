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


}
