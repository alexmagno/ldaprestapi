package com.technicalinterview.ldaprestapi;

import com.technicalinterview.ldaprestapi.domain.User;
import com.technicalinterview.ldaprestapi.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LdapRestApiApplicationTests {

	@Autowired
	private UserService userService;

	private static boolean initialized = false;

	@BeforeEach
	public void removeAllUsers() {
		if (!initialized) {
			userService.removeAll();
			initialized = true;
		}
	}

	@Test
	@Order(1)
	public void shouldCreateAnUser() {
		User user = new User("1234", "John", "Doe");
		User newUser = userService.create(user);

		assertNotNull(newUser);
		assertEquals(newUser.getUid(), "1234");
		assertEquals(newUser.getCn(), "John");
		assertEquals(newUser.getSn(), "Doe");
	}

	@Test
	@Order(2)
	public void shouldThrowAnErrorWhenCreatingSameUser() {
		User user = new User("1234", "John", "Doe");
		Assertions.assertThrows(NameAlreadyBoundException.class, () ->
			userService.create(user));
	}

	@Test
	@Order(3)
	public void shouldGetAnUser() {
		User user = userService.getUser("1234");

		assertNotNull(user);
		assertEquals(user.getUid(), "1234");
		assertEquals(user.getCn(), "John");
		assertEquals(user.getSn(), "Doe");
	}

	@Test
	@Order(4)
	public void shouldUpdateAnUser() {
		User user = new User("1234", "Foo", "Bar");
		User updatedUser = userService.update(user);

		assertNotNull(updatedUser);
		assertEquals(updatedUser.getUid(), "1234");
		assertEquals(updatedUser.getCn(), "Foo");
		assertEquals(updatedUser.getSn(), "Bar");
	}

	@Test
	@Order(5)
	public void shouldUsersListBeEmptyWhenRemovedTheOnlyUser() {
		userService.remove("1234");
		List<User> users = userService.getAllUsers();
		assertEquals(users.size(), 0);
	}

}