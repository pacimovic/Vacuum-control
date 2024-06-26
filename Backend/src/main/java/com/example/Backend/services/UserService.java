package com.example.Backend.services;

import com.example.Backend.model.User;
import com.example.Backend.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User, Long>, UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <S extends User> S save(S user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(Long userId) {
        this.userRepository.deleteById(userId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.userRepository.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email: "+email+" not found");
        }

        Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(myUser);

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), grantedAuthorities);
    }


    private Collection<GrantedAuthority> getGrantedAuthorities(User myUser){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(myUser.getPermission().isCan_create_users()) grantedAuthorities.add(new SimpleGrantedAuthority("can_create_users"));
        if(myUser.getPermission().isCan_read_users()) grantedAuthorities.add(new SimpleGrantedAuthority("can_read_users"));
        if(myUser.getPermission().isCan_update_users()) grantedAuthorities.add(new SimpleGrantedAuthority("can_update_users"));
        if(myUser.getPermission().isCan_delete_users()) grantedAuthorities.add(new SimpleGrantedAuthority("can_delete_users"));
        if(myUser.getPermission().isCan_search_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_search_vacuum"));
        if(myUser.getPermission().isCan_start_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_start_vacuum"));
        if(myUser.getPermission().isCan_stop_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_stop_vacuum"));
        if(myUser.getPermission().isCan_discharge_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_discharge_vacuum"));
        if(myUser.getPermission().isCan_add_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_add_vacuum"));
        if(myUser.getPermission().isCan_remove_vacuum()) grantedAuthorities.add(new SimpleGrantedAuthority("can_remove_vacuum"));

        return grantedAuthorities;
    }
}
