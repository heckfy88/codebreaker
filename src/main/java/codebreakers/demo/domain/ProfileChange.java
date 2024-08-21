package codebreakers.demo.domain;

import java.time.LocalDateTime;

public record ProfileChange(
    Long id,
    String parentId,
    String masterProfile,
    Long masterProfileVersion,
    String deactivatedProfileMerge,
    Long deactivatedProfileMergeVersion,
    String deactivatedProfileUnmerge,
    Long deactivatedProfileUnmergeVersion,
    String newProfile,
    String activatedProfile,
    Long activatedProfileVersion,
    OperationType operationType,
    LocalDateTime createDate
) {
}
