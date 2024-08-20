package codebreakers.demo.api.dto;

import java.util.List;

public record ProfileHistoryDto(
        String masterProfile,
        boolean isActive,
        List<DeactivatedProfileDto> deactivatedProfileDtos
) {
}
