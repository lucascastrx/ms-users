package com.ms.users;

import com.ms.users.domain.model.User;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.infra.adapter.repository.UserRepositoryAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SignUpUserTest {

    @Autowired
    private UserServicePort userService;

    @Autowired
    private UserRepositoryAccess databaseAccess;

    @BeforeEach
    public void setUp(){
        databaseAccess.deleteAllInBatch();
    }


    @Test
    public void shouldSucceedWhenUserSignUpWithAllData(){
        var user = new User();
        user.setName("Lucas");
        user.setUsername("lvcvx");
        user.setEmail("luukascastro@gmail.com");
        user.setPassword("lucas");

        user = userService.addUser(user);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFailWhenUserSignUpWithRepeatedEmail(){
        var user = new User();
        user.setName("Lucas");
        user.setUsername("lvcvx");
        user.setEmail("luukascastro@gmail.com");
        user.setPassword("lucas");

        user = userService.addUser(user);

        var secondUser = new User();
        secondUser.setName("Lucas");
        secondUser.setUsername("lvcvx");
        secondUser.setEmail("luukascastro@gmail.com");
        secondUser.setPassword("lucas");
        System.out.println("TESTE");


        IllegalStateException error = assertThrows(IllegalStateException.class,
                ()-> userService.addUser(secondUser));

        assertThat(error).isNotNull();

    }
}
