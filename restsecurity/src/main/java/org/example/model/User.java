package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String username;
    private String password;

    @ManyToMany(mappedBy = "users")
    Set<Role> roles;

    public User(String username, String password){
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Set<String> getRolesToString(){
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }


}
