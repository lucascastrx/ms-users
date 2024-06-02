package com.ms.users.UT.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.User;
import com.ms.users.infra.adapter.model.UserModel;
import com.ms.users.infra.adapter.repository.UserRepository;
import com.ms.users.infra.adapter.repository.UserRepositoryAccess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepositoryAccess userRepositoryAccess;

    @Mock
    private MapperDTO mapper;
//    @Autowired
    @InjectMocks
    private UserRepository userRepository;


//    @BeforeEach
//    public void setUp(){
//        this.userRepository = new UserRepository(this.userRepositoryAccess, new MapperDTO(new ModelMapper()));
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void shouldReturnUserWhenSavingUserWithMapper(){
        var user = new User();
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var userModel = new UserModel();
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var savedUserModel = new UserModel();
        savedUserModel.setId(1L);
        savedUserModel.setName("Lucas");
        savedUserModel.setUsername("Lucas");
        savedUserModel.setEmail("lucas@gmail.com");
        savedUserModel.setPassword("lucas");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");


        when(mapper.transform(any(User.class), same(UserModel.class))).thenReturn(userModel);
        when(userRepositoryAccess.save(any(UserModel.class))).thenReturn(savedUserModel);
        when(mapper.transform(any(UserModel.class), same(User.class))).thenReturn(savedUser);

        var testUser = userRepository.save(user);

        assertThat(testUser).isNotNull();
        assertThat(testUser.getId()).isGreaterThan(0);


    }

    @Test
    public void shouldReturnUserWhenFetchWithEmail(){
        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepositoryAccess.findByEmail(anyString())).thenReturn(Optional.of(userModel));
        when(mapper.transform(any(UserModel.class), same(User.class))).thenReturn(savedUser);

        var testUser = userRepository.findByEmail("lucas@gmail.com").get();
        assertThat(testUser).isNotNull();
    }

    @Test
    public void shouldReturnUserWhenFetchWithId(){
        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepositoryAccess.findById(anyLong())).thenReturn(Optional.of(userModel));
        when(mapper.transform(any(Optional.class), same(User.class))).thenReturn(savedUser);

        var testUser = userRepository.findById(1L).get();
        assertThat(testUser).isNotNull();
    }
}
