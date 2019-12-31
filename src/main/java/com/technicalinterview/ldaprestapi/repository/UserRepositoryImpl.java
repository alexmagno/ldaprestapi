package com.technicalinterview.ldaprestapi.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;

import com.technicalinterview.ldaprestapi.domain.User;
import com.technicalinterview.ldaprestapi.utils.UserAttributesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    public static final String BASE_DN = "dc=techinterview,dc=com";
    public static final String USERS_OU_DN = "ou=Users,dc=techinterview,dc=com";

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public List<User> getAll() {
        List<User> users = ldapTemplate.search(query().base(USERS_OU_DN).where("objectClass").is("inetOrgPerson"),
                new UserAttributesMapper());
        return users;
    }

    @Override
    public User getOne(String userId) {
        List<User> users = ldapTemplate.search(query().base(USERS_OU_DN).where("uid").is(userId),
                new UserAttributesMapper());

        if(!users.isEmpty())
            return users.get(0);

        return null;
    }

    @Override
    public User create(User user) {
        Name dn = buildDn(user.getUid());
        ldapTemplate.bind(dn, null, buildAttributes(user));
        return user;
    }

    @Override
    public User update(User user) {
        Name dn = buildDn(user.getUid());
        ldapTemplate.rebind(dn, null, buildAttributes(user));
        return this.getOne(user.getUid());
    }

    @Override
    public User remove(String userId) {
        User user = this.getOne(userId);
        Name dn = buildDn(userId);
        ldapTemplate.unbind(dn);
        return user;
    }

    @Override
    public void removeAll() {
        List<User> users = this.getAll();
        for(User user : users) {
            this.remove(user.getUid());
        }
    }

    private Attributes buildAttributes(User user) {
        BasicAttribute ocattr = new BasicAttribute("objectClass");
        ocattr.add("inetOrgPerson");
        ocattr.add("organizationalPerson");
        ocattr.add("person");
        ocattr.add("top");

        Attributes attrs = new BasicAttributes();
        attrs.put(ocattr);
        attrs.put("cn", user.getCn());
        attrs.put("sn", user.getSn());
        attrs.put("uid", user.getUid());

        return attrs;
    }

    public Name buildDn(String userId) {
        return LdapNameBuilder.newInstance(BASE_DN).add("ou", "Users").add("uid", userId).build();
    }
}