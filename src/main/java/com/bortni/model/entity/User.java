package com.bortni.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString(exclude = "games")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Pattern(regexp = "[\\w]{3,20}")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9+_.-]+@[a-z.-]+\\.[a-z]{2,8}")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Game> games = new HashSet<>();
}
