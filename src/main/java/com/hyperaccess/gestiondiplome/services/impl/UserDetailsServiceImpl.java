package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findUtilisateursByEmail(username).orElseThrow(() -> new EntityNotFoundException("No user was found with the provided email"));
    }
}
