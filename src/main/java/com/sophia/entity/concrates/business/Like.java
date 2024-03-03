package com.sophia.entity.concrates.business;

import com.sophia.entity.concrates.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "tbl_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Entry cannot be empty")
    @ManyToOne
    private Entry entry;

    @NotNull(message = "There must be a user")
    @ManyToOne
    private User user;
}
