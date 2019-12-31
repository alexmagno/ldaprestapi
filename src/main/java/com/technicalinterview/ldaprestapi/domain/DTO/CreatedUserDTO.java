package com.technicalinterview.ldaprestapi.domain.DTO;

import com.technicalinterview.ldaprestapi.domain.User;

public class CreatedUserDTO {

    User user;
    String uri;

    public CreatedUserDTO(User user, String uri) {
        this.user = user;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "{" +
                  user +
                ", " + uri + '\'' +
                '}';
    }
}
