package codebreakers.demo.service.impl;

import codebreakers.demo.api.dto.ProfileHistoryDto;
import codebreakers.demo.domain.ProfileChange;
import codebreakers.demo.domain.ProfileHistory;
import codebreakers.demo.repository.ProfileHistoryRepository;
import codebreakers.demo.service.ProfileHistoryService;
import codebreakers.demo.utils.DomainDtoConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileHistoryServiceImpl implements ProfileHistoryService {

    private final ProfileHistoryRepository profileHistoryDao;
    private final DomainDtoConverter dtoConverter;

    public ProfileHistoryServiceImpl(ProfileHistoryRepository profileHistoryDao, DomainDtoConverter dtoConverter) {
        this.profileHistoryDao = profileHistoryDao;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public void processProfileEvent(ProfileChange profileChange) {
        profileHistoryDao.save(profileChange);
    }

    @Override
    public ProfileHistoryDto getProfileHistory(String profile) {
        ProfileHistory masterClient = profileHistoryDao.getMasterClient(profile);
        if (masterClient == null) {
            return new ProfileHistoryDto(
                    profile,
                    true,
                    null
            );
        } else {
            List<ProfileHistory> childClients = profileHistoryDao.getChildClients(profile);
            return new ProfileHistoryDto(
                    masterClient.profile(),
                    masterClient.isActive(),
                    dtoConverter.convert(childClients)
            );
        }
    }
}
