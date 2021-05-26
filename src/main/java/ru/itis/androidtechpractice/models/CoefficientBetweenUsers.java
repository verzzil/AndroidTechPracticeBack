package ru.itis.androidtechpractice.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@EqualsAndHashCode(exclude = {"firstUser", "secondUser"})
public class CoefficientBetweenUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float coefficient;
    @Enumerated(value = EnumType.STRING)
    private RelationType relationType;

    @ManyToOne
    @JoinColumn(name = "first_user")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user")
    private User secondUser;

    public enum RelationType {
        ACQUAINTANCES, FRIENDS, BEST_FRIENDS
    }
}
