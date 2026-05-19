package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.model.UserEntity;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRespository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"+" "+email));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+userEntity.getRole().name()));
        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
