package ru.itis.androidtechpractice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"groups", "chats", "fromCoef", "toCoef", "group", "token"})
@EqualsAndHashCode(exclude = {"groups", "chats", "fromCoef", "toCoef", "group", "token"})
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Long birthday;
    private Long dateOfRegistration;
    private Long cash;
    @Enumerated(value = EnumType.STRING)
    private State confirmStatus;
    private Float selfCoefficient;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String hashPassword;
    private String photoLink;
    private String firebaseToken;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

    @ManyToMany
    @JoinTable(
            name = "users_chats_rel",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")}
    )
    private List<Chat> chats;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @OneToMany(mappedBy = "user")
    private List<UserAct> userAct;

    @OneToMany(mappedBy = "firstUser")
    private List<CoefficientBetweenUsers> fromCoef;

    @OneToMany(mappedBy = "secondUser")
    private List<CoefficientBetweenUsers> toCoef;

    @OneToMany
    private List<Group> organizedGroups;

    @OneToOne(mappedBy = "user")
    private Token token;

    @OneToMany(mappedBy = "user")
    private List<SocialLink> socialLinks;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public enum Role {
        ADMIN, MODERATOR, USER
    }

    public enum State {
        NOT_CONFIRMED, CONFIRMED
    }

    public boolean isActive() {
        return this.confirmStatus == State.CONFIRMED;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
