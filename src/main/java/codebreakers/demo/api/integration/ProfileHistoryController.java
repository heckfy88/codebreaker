package codebreakers.demo.api.integration;

import codebreakers.demo.api.dto.ProfileHistoryDto;
import codebreakers.demo.service.ProfileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/integration/v1/profile")
public class ProfileHistoryController {

    private final ProfileHistoryService profileHistoryService;

    @Autowired
    public ProfileHistoryController(ProfileHistoryService profileHistoryService) {
        this.profileHistoryService = profileHistoryService;
    }

    @GetMapping("/{profile}")
    ProfileHistoryDto getProfileHistory(@PathVariable String profile) {
        return profileHistoryService.getProfileHistory(profile);
    }
}
