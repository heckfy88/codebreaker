package codebreakers.demo.repository;

import codebreakers.demo.domain.ProfileChange;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static jooq.Tables.PROFILE_CHANGE;

@Repository
public class ProfileChangeRepository {

    DSLContext dslContext;

    @Transactional
    public void save(ProfileChange profileChange) {
        dslContext.insertInto(
                PROFILE_CHANGE,
                PROFILE_CHANGE.PARENT_ID,
                PROFILE_CHANGE.MASTER_PROFILE,
                PROFILE_CHANGE.MASTER_PROFILE_VERSION,
                PROFILE_CHANGE.DEACTIVATED_PROFILE_MERGE,
                PROFILE_CHANGE.DEACTIVATED_PROFILE_MERGE_VERSION,
                PROFILE_CHANGE.DEACTIVATED_PROFILE_UNMERGE,
                PROFILE_CHANGE.DEACTIVATED_PROFILE_UNMERGE_VERSION,
                PROFILE_CHANGE.NEW_PROFILE,
                PROFILE_CHANGE.ACTIVATED_PROFILE,
                PROFILE_CHANGE.ACTIVATED_PROFILE_VERSION,
                PROFILE_CHANGE.OPERATION_TYPE,
                PROFILE_CHANGE.CREATE_DATE
            )
            .values(
                profileChange.parentId(),
                profileChange.masterProfile(),
                profileChange.masterProfileVersion(),
                profileChange.deactivatedProfileMerge(),
                profileChange.deactivatedProfileMergeVersion(),
                profileChange.deactivatedProfileUnmerge(),
                profileChange.deactivatedProfileUnmergeVersion(),
                profileChange.newProfile(),
                profileChange.activatedProfile(),
                profileChange.activatedProfileVersion(),
                profileChange.operationType().name(),
                profileChange.createDate()
            ).execute();
    }

}
