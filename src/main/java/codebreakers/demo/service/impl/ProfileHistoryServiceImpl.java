package codebreakers.demo.service.impl;

import codebreakers.demo.api.dto.ProfileHistoryDto;
import codebreakers.demo.service.ProfileHistoryService;
import org.springframework.stereotype.Service;

@Service
public class ProfileHistoryServiceImpl implements ProfileHistoryService {
    @Override
    public void processProfileEvent(ProfileChange profileChange) {

    }

    @Override
    public ProfileHistoryDto getProfileHistory(String profile) {
        return null;
    }
}
