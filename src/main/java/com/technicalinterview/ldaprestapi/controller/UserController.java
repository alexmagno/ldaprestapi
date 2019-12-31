package com.technicalinterview.ldaprestapi.controller;

import java.util.List;

import com.technicalinterview.ldaprestapi.domain.DTO.CreatedUserDTO;
import com.technicalinterview.ldaprestapi.domain.User;
import com.technicalinterview.ldaprestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/Users")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        if(users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/Users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(name = "userId") String userId) {

        User user = userService.getUser(userId);

        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("/Users")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        User createdUser = userService.create(user);

        String uri = "uri = 'http//:localhost/Users/"+createdUser.getUid();

        return new ResponseEntity<>(new CreatedUserDTO(createdUser, uri).toString(), HttpStatus.CREATED);
    }

    @PutMapping("/Users/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(name = "userId") String userId, @RequestBody User user) {

        User userToUpdate = userService.getUser(userId);

        if(userToUpdate == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        userToUpdate.setCn(user.getCn());
        userToUpdate.setSn(user.getSn());

        User updatedUser = userService.update(userToUpdate);
        return new ResponseEntity<>(updatedUser.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/Users/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable(name = "userId") String userId) {
        userService.remove(userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/Users")
    public ResponseEntity removeAllUsers() {
        userService.removeAll();
        return new ResponseEntity(HttpStatus.OK);
    }

}
