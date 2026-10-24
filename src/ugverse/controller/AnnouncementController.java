
package ugverse.controller;

import java.util.List;
import ugverse.model.AnnouncementModel;


public class AnnouncementController {

    // Publish announcement - Faculty only
    public boolean createAnnouncement(
            int userId,
            String role,
            String title,
            String content) {

        if (userId <= 0) {
            return false;
        }

        if (role == null || !role.equalsIgnoreCase("Faculty")) {
            return false;
        }

        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        if (title.trim().length() > 200) {
            return false;
        }

        return AnnouncementModel.createAnnouncement(
                userId,
                title.trim(),
                content.trim()
        );
    }

    // View announcements
    public List<AnnouncementModel> getAllAnnouncements() {
        return AnnouncementModel.getAllAnnouncements();
    }
}
