package com.jmt.chat.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String email;

    private String provider;
}
