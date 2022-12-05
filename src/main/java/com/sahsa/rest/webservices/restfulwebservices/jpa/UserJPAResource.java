package com.sahsa.rest.webservices.restfulwebservices.jpa;


import com.sahsa.rest.webservices.restfulwebservices.user.Post;
import com.sahsa.rest.webservices.restfulwebservices.user.User;
import com.sahsa.rest.webservices.restfulwebservices.user.UserDAOService;
import com.sahsa.rest.webservices.restfulwebservices.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJPAResource {

    @Autowired
    public UserJPAResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserRepository userRepository;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveAllUsers(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        EntityModel<User> entityModel= EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return  entityModel;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostForUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user.get().getPosts();
    }

    @DeleteMapping ("/users/{id}")
    public void deleteUsers(@PathVariable int id) {
       userRepository.deleteById(id);

    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
