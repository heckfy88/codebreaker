package codebreakers.demo.service;

import codebreakers.demo.api.dto.ProfileHistoryDto;
import codebreakers.demo.domain.ProfileChange;

public interface ProfileHistoryService {
    void processProfileEvent(ProfileChange profileChange);
    ProfileHistoryDto getProfileHistory(String profile);
}
