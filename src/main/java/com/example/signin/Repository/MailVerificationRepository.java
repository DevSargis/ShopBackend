package com.example.signin.Repository;

import com.example.signin.Entities.MailVerifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailVerificationRepository extends JpaRepository<MailVerifier, Long> {

}
