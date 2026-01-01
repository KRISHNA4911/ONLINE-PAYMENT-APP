package com.example.payment.securerepository;

import com.example.payment.secureentity.SecureAdminCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureAdminCredentialRepository
        extends JpaRepository<SecureAdminCredential, Integer> {
}
