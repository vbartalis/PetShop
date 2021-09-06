package io.github.vbartalis.petshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;

    private String address;

    private String description;

    private Date joinDate;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProfileImage profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Profile(User user) {
        this.joinDate = new Date();
        this.user = user;
        this.profileImage = new ProfileImage(this);
    }
}
