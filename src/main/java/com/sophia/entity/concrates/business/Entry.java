package com.sophia.entity.concrates.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sophia.entity.concrates.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "tbl_entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Topic topic;

    @NotNull(message = "Content cannot be empty")
    private String content;

    private String tags;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
    private List<Dislike> dislikes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate;

}
