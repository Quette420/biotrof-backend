package ru.volgau.graduatework.biotrofbackend.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ru.volgau.graduatework.biotrofbackend.dictionary.Role;
import ru.volgau.graduatework.biotrofbackend.dictionary.Status;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(of = "uuid")
@Table(name = "employers")
public class Employer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;

    @Column(name = "username", unique=true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
