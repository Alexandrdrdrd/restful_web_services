package com.sahsa.rest.webservices.restfulwebservices.user;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    public UserResource(UserDAOService userDAOService) {
        this.userDAOService = userDAOService;
    }

    UserDAOService userDAOService;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAOService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveAllUsers(@PathVariable int id) {
        User user = userDAOService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        EntityModel<User> entityModel= EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return  entityModel;
    }

    @DeleteMapping ("/users/{id}")
    public void deleteUsers(@PathVariable int id) {
       userDAOService.deleteById(id);

    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userDAOService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
