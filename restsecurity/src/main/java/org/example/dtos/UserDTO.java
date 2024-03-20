package org.example.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.User;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;
    private Set<String> roles;

    public UserDTO(String username, Set<String> roles){
        this.username = username;
        this.roles = roles;
    }

    public UserDTO(User user){
        this.username = user.getUsername();
        this.roles = user.getRolesToString();
    }


}
