package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository", userRepository);
        TestUtils.injectObjects(userController,"cartRepository", cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void createUserHappyPath() {
        when(bCryptPasswordEncoder.encode("testpassword")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Sina");
        request.setPassword("testpassword");
        request.setConfirmPassword("testpassword");

        ResponseEntity<User> response =  userController.createUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertEquals(0,user.getId());
        assertEquals("Sina",user.getUsername());
        assertEquals("thisIsHashed",user.getPassword());
    }

    @Test
    public void findByUserNameTest() {
        when(userRepository.findByUsername("test")).thenReturn(new User(0,"test","testpassword",new Cart()));
        ResponseEntity<User> response =  userController.findByUserName("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals(0,user.getId());
        assertEquals("test",user.getUsername());
        assertEquals("testpassword",user.getPassword());
    }

    @Test
    public void findByIdTest() {
        when(userRepository.findById(0L)).thenReturn(Optional.of(new User(0, "test", "testpassword", new Cart())));
        ResponseEntity<User> response =  userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals(0,user.getId());
        assertEquals("test",user.getUsername());
        assertEquals("testpassword",user.getPassword());
    }
}

