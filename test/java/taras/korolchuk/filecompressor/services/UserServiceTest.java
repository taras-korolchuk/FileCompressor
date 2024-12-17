package taras.korolchuk.filecompressor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import taras.korolchuk.filecompressor.api.model.UserModel;
import taras.korolchuk.filecompressor.domain.entity.UserEntity;
import taras.korolchuk.filecompressor.domain.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ObjectMapper objectMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, objectMapper);
    }

    @Test
    void testRegisterUser() {
        UserModel userModel = new UserModel("Test", "Test");
        UserEntity user = new UserEntity();

        when(objectMapper.convertValue(userModel, UserEntity.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(objectMapper.convertValue(user, UserModel.class)).thenReturn(userModel);

        UserModel result = userService.registerUser(userModel);

        assertEquals(userModel, result);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        Optional<UserEntity> user = Optional.of(new UserEntity(/* parameters */));
        when(userRepository.findById(userId)).thenReturn(user);

        Optional<UserEntity> result = userService.getUserById(userId);

        assertEquals(user, result);
    }

    @Test
    void testLoginUser() {
        String username = "testUser";
        String password = "password";
        Optional<UserEntity> user = Optional.of(new UserEntity());
        user.get().setId(1L);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(user);

        Optional<Long> result = userService.loginUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
