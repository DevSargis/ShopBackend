package com.example.signin.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_verifier")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MailVerifier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "code")
    private String Code;

    @Column(name = "createdAt")
    private Long createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
