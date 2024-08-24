package codebreakers.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileHistoryDto(
        String masterProfile,
        boolean isActive,
        List<DeactivatedProfileDto> deactivatedProfileDtos
) {
}
