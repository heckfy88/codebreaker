package codebreakers.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DeactivatedProfileDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        String deactivatedProfile,
        LocalDateTime updateDate
) {}

