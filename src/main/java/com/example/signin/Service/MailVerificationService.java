package com.example.signin.Service;

import com.example.signin.Entities.MailVerifier;
import com.example.signin.Repository.MailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailVerificationService {

    @Autowired
    MailVerificationRepository mailVerificationRepository;

    public void save(MailVerifier mailVerifier){
        mailVerificationRepository.save(mailVerifier);
    }
}
