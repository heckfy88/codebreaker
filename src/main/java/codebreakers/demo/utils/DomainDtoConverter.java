package codebreakers.demo.utils;

import codebreakers.demo.api.dto.DeactivatedProfileDto;
import codebreakers.demo.domain.ProfileHistory;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class DomainDtoConverter {

    public static final Converter<ProfileHistory, DeactivatedProfileDto> profileHistoryToDeactivatedProfileDtoConverter =
            new AbstractConverter<>() {
                @Override
                protected DeactivatedProfileDto convert(ProfileHistory profileHistory) {
                    return profileHistory != null ?
                            new DeactivatedProfileDto(
                                    profileHistory.profile(),
                                    profileHistory.updateDate()
                            ) : null;
                }
            };

    public static final ModelMapper modelMapper = new ModelMapper();
    public static final Type deactivatedProfileDtoType = new TypeToken<List<DeactivatedProfileDto>>() {
    }.getType();

    static {
        modelMapper.addConverter(profileHistoryToDeactivatedProfileDtoConverter);
    }

    public List<DeactivatedProfileDto> convert(List<ProfileHistory> deactivatedProfileHistories) {
        return modelMapper.map(deactivatedProfileHistories, deactivatedProfileDtoType);
    }
}
