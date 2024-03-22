package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {

    @Id
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    List<User> users;

    public void addUser(User user) {
        if(user != null && !users.contains(user)){
            users.add(user);
        }
    }
}
