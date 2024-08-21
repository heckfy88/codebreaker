package codebreakers.demo.domain;

import java.time.LocalDateTime;

public record ProfileHistory(
    String profile,
    Long profileVersion,
    boolean isActive,
    String parentProfile,
    LocalDateTime updateDate
) {
}