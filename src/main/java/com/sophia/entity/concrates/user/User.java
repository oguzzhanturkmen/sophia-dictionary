package com.sophia.entity.concrates.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sophia.entity.concrates.business.Dislike;
import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Like;
import com.sophia.entity.concrates.business.Topic;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Entry> entries;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Topic> topics;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "tbl_user_followers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private Set<Like> likes;

    @OneToMany(mappedBy = "user")
    private Set<Dislike> dislikes;

    private String bio;
    @Lob
    private byte[] profileImage;


}
