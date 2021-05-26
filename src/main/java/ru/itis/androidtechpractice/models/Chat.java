package ru.itis.androidtechpractice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"users", "messages"})
@EqualsAndHashCode(exclude = {"users", "messages"})
@Entity
public class Chat {
    @Id
    @GeneratedValue
    private Integer id;
    private Date dateOfCreation;
    private String title;
    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @ManyToMany
    @JoinTable(
            name = "users_chats_rel",
            joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> users;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public enum ChatType {
        TWO, GROUP
    }
}
