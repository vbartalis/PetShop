package io.github.vbartalis.petshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * This is an entity class. It represents a table stored in the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Profile profile;

    public ProfileImage(Profile profile) {
        this.profile = profile;
    }

}
