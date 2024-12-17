package taras.korolchuk.filecompressor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModel {
    private String username;
    private String password;
}
