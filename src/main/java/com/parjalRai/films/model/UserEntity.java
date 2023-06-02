package com.parjalRai.films.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.parjalRai.films.security.token.Token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    private ObjectId id;
    @NotBlank(message = "Username is a required field")
    @Size(min = 4, max = 20, message = "Username is not valid, please use 4-20 characters")
    @Indexed(unique = true)
    private String username;
    private String email;
    @Size(min = 8, max = 30, message = "Password is not valid, please use 8-30 characters")
    private String password;
    private Role role;
    private String profile_pic;
    private List<Token> tokens; 


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    


}
