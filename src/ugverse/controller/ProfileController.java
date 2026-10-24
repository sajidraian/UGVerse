
package ugverse.controller;

import java.sql.SQLException;
import ugverse.model.ProfileModel;

public class ProfileController {

    public ProfileModel getProfile(int userId) throws SQLException {
        ProfileModel profile = new ProfileModel();

        if (profile.loadProfile(userId)) {
            return profile;
        }

        profile.setDepartment("");
        profile.setBatch("");
        profile.setBio("");
        profile.setPhone("");

        return profile;
    }

    public boolean saveProfile(
            int userId,
            String department,
            String batch,
            String bio,
            String phone
    ) throws SQLException {

        if (userId <= 0
                || department == null || department.isBlank()) {
            return false;
        }

        ProfileModel profile = new ProfileModel();
        profile.setDepartment(department.trim());
        profile.setBatch(batch == null ? "" : batch.trim());
        profile.setBio(bio == null ? "" : bio.trim());
        profile.setPhone(phone == null ? "" : phone.trim());

        profile.saveProfile(userId);
        return true;
    }
}
