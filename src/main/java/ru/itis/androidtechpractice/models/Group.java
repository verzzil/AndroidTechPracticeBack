package ru.itis.androidtechpractice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"users", "groupAct", "mainUser"})
@EqualsAndHashCode(exclude = {"users", "groupAct", "mainUser"})
@Table(name = "group_users")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Date dateOfCreation;
    @ManyToMany
    @JoinTable(
            name = "users_groups_rel",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> users;

    @OneToMany(mappedBy = "group")
    private List<GroupAct> groupAct;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User mainUser;
}
