package com.technicalinterview.ldaprestapi.utils;

import com.technicalinterview.ldaprestapi.domain.User;
import org.springframework.ldap.core.AttributesMapper;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class UserAttributesMapper implements AttributesMapper<User> {

    @Override
    public User mapFromAttributes(Attributes attributes) throws NamingException {
        User user = new User();
        user.setUid(attributes.get("uid") != null ? attributes.get("uid").get().toString() : null);
        user.setCn(attributes.get("cn") != null ? attributes.get("cn").get().toString() : null);
        user.setSn(attributes.get("sn") != null ? attributes.get("sn").get().toString() : null);

        return user;
    }
}
