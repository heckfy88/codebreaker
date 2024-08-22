package codebreakers.demo.service.impl;

import codebreakers.demo.domain.ProfileChange;
import codebreakers.demo.repository.ProfileChangeRepository;
import codebreakers.demo.service.ProfileChangeService;
import org.springframework.stereotype.Service;

@Service
public class ProfileChangeServiceImpl implements ProfileChangeService {

    private final ProfileChangeRepository profileChangeRepository;

    public ProfileChangeServiceImpl(ProfileChangeRepository profileChangeRepository) {
        this.profileChangeRepository = profileChangeRepository;
    }

    public void save(ProfileChange profileChange) {
        profileChangeRepository.save(profileChange);
    }
}
