package io.github.vbartalis.petshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private Date creationDate;

    private Date updateDate;

    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private PostImage postImage;

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    public Post(@NotBlank String title, @NotBlank String description, User user, Date creationDate, Boolean isPublic, List<Tag> tags) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.creationDate = creationDate;
        this.updateDate = creationDate;
        this.isPublic = isPublic;
        this.postImage = new PostImage(this);
        this.tags = tags;
    }

}
