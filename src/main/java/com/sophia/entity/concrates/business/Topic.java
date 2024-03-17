package com.sophia.entity.concrates.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sophia.entity.concrates.user.User;

import lombok.*;
import org.hibernate.annotations.Cascade;
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
@Table(name = "tbl_topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Creation cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)

    private List<Entry> entries;

    @NotNull(message = "Author cannot be empty")
    @ManyToOne
    private User author;

    @NotNull(message = "Last update date cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "tbl_topic_tags",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_channel_id")
    )
    private List<ChannelTag> channelTags;


}
