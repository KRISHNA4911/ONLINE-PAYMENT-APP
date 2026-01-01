
package com.example.payment.secureservice;

import com.example.payment.secureentity.SecureAdminCredential;
import com.example.payment.securerepository.SecureAdminCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SecureAdminCredentialService implements UserDetailsService {

    @Autowired
    private SecureAdminCredentialRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Store password "491" once on startup
    @PostConstruct
    public void initAdminPassword() {
        if (!repo.existsById(1)) {
            SecureAdminCredential admin = new SecureAdminCredential();
            admin.setId(1);
            admin.setPassword(passwordEncoder.encode("491"));
            repo.save(admin);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecureAdminCredential admin = repo.findById(1)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return User.builder()
                .username("ADMIN")
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}
