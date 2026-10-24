
package ugverse.controller;

import java.sql.SQLException;
import ugverse.model.LoginModel;

public class LoginController {

    private final LoginModel loginModel;

    public LoginController() {
        loginModel = new LoginModel();
    }

    public LoginModel authenticate(String email, String password)
            throws SQLException {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        boolean success = loginModel.login(
                email.trim(), password
        );

        if (success) {
            return loginModel;
        }

        return null;
    }
}
