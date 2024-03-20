package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    private String name;

    @ManyToMany
    List<User> users;

}
