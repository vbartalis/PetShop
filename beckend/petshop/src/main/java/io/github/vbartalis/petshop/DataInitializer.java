package io.github.vbartalis.petshop;

import io.github.vbartalis.petshop.entity.*;
import io.github.vbartalis.petshop.repository.entityRepository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ProfileImageRepository profileImageRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.JANUARY, 1, 0, 0, 0);

        /*-----------------------------------------------------*/
        // Create Roles
        /*-----------------------------------------------------*/
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        roleRepository.findAll();
        /*-----------------------------------------------------*/
        // Create Users
        /*-----------------------------------------------------*/

        User user1 = new User(
                "admin",
                this.passwordEncoder.encode("pwd"),
                false,
                DateUtils.addMonths(cal.getTime(), 3),
                Arrays.asList(roleUser, roleAdmin)

        );

        User user2 = new User(
                "user2",
                this.passwordEncoder.encode("pwd"),
                false,
                DateUtils.addMonths(cal.getTime(), 2),
                Arrays.asList(roleUser)
        );

        User user3 = new User(
                "user3",
                this.passwordEncoder.encode("pwd"),
                false,
                DateUtils.addMonths(cal.getTime(), 2),
                Arrays.asList(roleUser)
        );

        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);

        /*-----------------------------------------------------*/
        // Create Profiles For Users
        /*-----------------------------------------------------*/

        Profile profile1 = this.userRepository.findByUsername(user1.getUsername()).get().getProfile();
        profile1.setName("Admin Admin");
        profile1.setEmail("admin@email.com");
        profile1.setAddress("221B Baker St, London, England");
        profile1.setDescription("I'm an admin.");
        profileRepository.save(profile1);

        Profile profile2 = this.userRepository.findByUsername(user2.getUsername()).get().getProfile();
        profile2.setName("Tom Smith");
        profile2.setEmail("tomsmith@email.com");
        profile2.setAddress("350 Fifth Avenue New York, NY 10118.");
        profile2.setDescription("I love dogs and I'm allergic to cats.");
        profileRepository.save(profile2);

        Profile profile3 = this.userRepository.findByUsername(user3.getUsername()).get().getProfile();
        profile3.setName("James Brown");
        profile3.setEmail("jamesbrown@email.com");
        profile3.setAddress("11 Wall Street New York, NY.");
        profile3.setDescription("I like to eat.");
        profileRepository.save(profile3);

        /*-----------------------------------------------------*/
        // Create Tags
        /*-----------------------------------------------------*/

        Tag tag1 = new Tag("Dog", "Canis lupus familiaris");
        Tag tag2 = new Tag("Cat", "Felis catus");
        Tag tag3 = new Tag("Black", "The pet is colored black at least partially");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        /*-----------------------------------------------------*/
        // Create Posts
        /*-----------------------------------------------------*/

        cal.set(2021, Calendar.JANUARY, 2, 0, 0, 0);
        Post post1 = new Post("Dogs for free", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1, tag3));
        cal.set(2021, Calendar.JANUARY, 2, 0, 0, 0);
        Post post2 = new Post("Cats for free", "I have a lot of cats and i don't need them.", user2, cal.getTime(), false, List.of(tag2, tag3));

        postRepository.save(post1);
        postRepository.save(post2);

        cal.set(2021, Calendar.JANUARY, 3, 0, 0, 0);
        Post post3 = new Post("Dogs for 3", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), false, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 1, 0, 0);
        Post post4 = new Post("Dogs for 4", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 2, 0, 0);
        Post post5 = new Post("Dogs for 5", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 3, 0, 0);
        Post post6 = new Post("Dogs for 6", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 4, 0, 0);
        Post post7 = new Post("Dogs for 7", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 5, 0, 0);
        Post post8 = new Post("Dogs for 8", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 6, 0, 0);
        Post post9 = new Post("Dogs for 9", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 7, 0, 0);
        Post post10 = new Post("Dogs for 10", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 8, 0, 0);
        Post post11 = new Post("Dogs for 11", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 9, 0, 0);
        Post post12 = new Post("Dogs for 12", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 3, 10, 0, 0);

        Post post13 = new Post("Dogs for 13", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 0, 0, 0);
        Post post14 = new Post("Dogs for 14", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 1, 0, 0);
        Post post15 = new Post("Dogs for 15", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 2, 0, 0);
        Post post16 = new Post("Dogs for 16", "I have a lot of dogs and i don't need them.", user3, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 3, 0, 0);
        Post post17 = new Post("Dogs for 17", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 4, 0, 0);
        Post post18 = new Post("Dogs for 18", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 5, 0, 0);
        Post post19 = new Post("Dogs for 19", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 5, 6, 0, 0);
        Post post20 = new Post("Dogs for 20", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));

        cal.set(2021, Calendar.JANUARY, 7, 0, 0, 0);
        Post post21 = new Post("Dogs for 21", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 7, 1, 0, 0);
        Post post22 = new Post("Dogs for 22", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));
        cal.set(2021, Calendar.JANUARY, 7, 2, 0, 0);
        Post post23 = new Post("Dogs for 23", "I have a lot of dogs and i don't need them.", user2, cal.getTime(), true, List.of(tag1));

        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);
        postRepository.save(post8);
        postRepository.save(post9);
        postRepository.save(post10);

        postRepository.save(post11);
        postRepository.save(post12);
        postRepository.save(post13);
        postRepository.save(post14);
        postRepository.save(post15);
        postRepository.save(post16);
        postRepository.save(post17);
        postRepository.save(post18);
        postRepository.save(post19);
        postRepository.save(post20);

        postRepository.save(post21);
        postRepository.save(post22);
        postRepository.save(post23);

        /*-----------------------------------------------------*/
//
//
//        PostImage postImage1 = new PostImage(post1);
//        PostImage postImage2 = new PostImage(post2);
//
//        postImageRepository.save(postImage1);
//        postImageRepository.save(postImage2);
//
        /*-----------------------------------------------------*/


    }
}
