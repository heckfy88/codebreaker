package codebreakers.demo.service;

import codebreakers.demo.api.dto.ProfileHistoryDto;

public interface ProfileHistoryService {
    void processProfileEvent(ProfileChange profileChange);
    ProfileHistoryDto getProfileHistory(String profile);
}
