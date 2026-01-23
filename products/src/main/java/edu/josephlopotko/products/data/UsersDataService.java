package edu.josephlopotko.products.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.josephlopotko.products.models.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersDataService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from database
        UserEntity userEntity = usersRepository.findByUsername(username);
        
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        // Map role to GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
        
        // Create UserDetails with username, password, and authorities
        return User.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .disabled(!userEntity.isEnabled())
            .authorities(authorities)
            .build();
            //pg 27
    }
}
