package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.website.Authority;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.repositories.website.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByMtaUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getMtaUsername(), user.getPassword(), mapAuthorities(user.getAuthorities())
        );
    }

    private Collection< ? extends GrantedAuthority> mapAuthorities(Collection <Authority> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).toList();
    }
}
