package taras.korolchuk.filecompressor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taras.korolchuk.filecompressor.api.model.UserModel;
import taras.korolchuk.filecompressor.domain.entity.UserEntity;
import taras.korolchuk.filecompressor.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public UserModel registerUser(UserModel userModel) {
        // Add logic to hash the password before saving
        UserEntity converted = objectMapper.convertValue(userModel, UserEntity.class);
        converted = userRepository.save(converted);
        userModel = objectMapper.convertValue(converted, UserModel.class);
        return userModel;
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Long> loginUser(String username, String password) {
        Optional<UserEntity> converted = userRepository.findByUsernameAndPassword(username, password);
        return converted.map(UserEntity::getId);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

