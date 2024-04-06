package netology.taskboot.controller;

import netology.taskboot.config.JavaConfig;
import netology.taskboot.service.SystemProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// ProfileController.java
@RestController
public class ProfileController {
    private final SystemProfile profile;

    @Autowired
    public ProfileController(SystemProfile profile) {
        this.profile = profile;
    }

    @GetMapping("/profile")
    public String getProfile() {
        return profile.getProfile();
    }

    @GetMapping("/public")
    public String getPublic() {
        return "Публичная страница";
    }

    @GetMapping("/public/any")
    public String getPublicAny() {
        return "Та же страница что и в паблик";
    }

    @GetMapping("/read")
    public String getRead() {
        return "This is read page";
    }

    @GetMapping("/write")
    public String getWritePage() {
        return "This is write page";
    }
}
