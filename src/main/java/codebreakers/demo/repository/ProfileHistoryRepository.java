package codebreakers.demo.repository;

import codebreakers.demo.domain.ProfileChange;
import codebreakers.demo.domain.ProfileHistory;
import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static jooq.Tables.PROFILE_HISTORY;
import static org.jooq.impl.DSL.*;

public class ProfileHistoryRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Long NEW_CLIENT_PROFILE = 1L;

    DSLContext dslContext;

    CommonTableExpression<Record5<String, Long, Boolean, String, LocalDateTime>> getMasterClientCte(String profile) {
        return name("parent").fields(
            PROFILE_HISTORY.PROFILE.getName(),
            PROFILE_HISTORY.PROFILE_VERSION.getName(),
            PROFILE_HISTORY.IS_ACTIVE.getName(),
            PROFILE_HISTORY.PARENT_PROFILE.getName(),
            PROFILE_HISTORY.UPDATE_DATE.getName()
        ).as(
            select(
                PROFILE_HISTORY.PROFILE,
                PROFILE_HISTORY.PROFILE_VERSION,
                PROFILE_HISTORY.IS_ACTIVE,
                PROFILE_HISTORY.PARENT_PROFILE,
                PROFILE_HISTORY.UPDATE_DATE
            ).from(PROFILE_HISTORY)
                .where(PROFILE_HISTORY.PROFILE.eq(profile))
                .unionAll(
                    select(
                        PROFILE_HISTORY.PROFILE,
                        PROFILE_HISTORY.PROFILE_VERSION,
                        PROFILE_HISTORY.IS_ACTIVE,
                        PROFILE_HISTORY.PARENT_PROFILE,
                        PROFILE_HISTORY.UPDATE_DATE
                    ).from(PROFILE_HISTORY)
                        .join(table(name("parent")))
                        .on(
                            field(
                                name("parent", PROFILE_HISTORY.PARENT_PROFILE.getName()),
                                PROFILE_HISTORY.PARENT_PROFILE.getDataType()
                            )
                                .eq(PROFILE_HISTORY.PROFILE)
                        )
                )
        );
    }

    CommonTableExpression<Record5<String, Long, Boolean, String, LocalDateTime>> getChildClientCte(String profile) {
        return name("deactivated_profiles").fields(
            PROFILE_HISTORY.PROFILE.getName(),
            PROFILE_HISTORY.PROFILE_VERSION.getName(),
            PROFILE_HISTORY.IS_ACTIVE.getName(),
            PROFILE_HISTORY.PARENT_PROFILE.getName(),
            PROFILE_HISTORY.UPDATE_DATE.getName()
        ).as(
            select(
                PROFILE_HISTORY.PROFILE,
                PROFILE_HISTORY.PROFILE_VERSION,
                PROFILE_HISTORY.IS_ACTIVE,
                PROFILE_HISTORY.PARENT_PROFILE,
                PROFILE_HISTORY.UPDATE_DATE
            ).from(PROFILE_HISTORY)
                .where(PROFILE_HISTORY.PARENT_PROFILE.eq(profile))
                .unionAll(
                    select(
                        PROFILE_HISTORY.PROFILE,
                        PROFILE_HISTORY.PROFILE_VERSION,
                        PROFILE_HISTORY.IS_ACTIVE,
                        PROFILE_HISTORY.PARENT_PROFILE,
                        PROFILE_HISTORY.UPDATE_DATE
                    ).from(PROFILE_HISTORY)
                        .join(table(name("deactivated_profiles")))
                        .on(
                            field(
                                name("deactivated_profiles", PROFILE_HISTORY.PROFILE.getName()),
                                PROFILE_HISTORY.PARENT_PROFILE.getDataType()
                            )
                                .eq(PROFILE_HISTORY.PARENT_PROFILE)
                        )
                )
        );
    }

    @Transactional
    public void save(ProfileChange profileChange) {
        switch (profileChange.operationType()) {
            case MERGE -> mergeClients(profileChange);
            case UNMERGE -> unmergeClients(profileChange);
            default -> throw new RuntimeException("Unknown operation type: " + profileChange.operationType());
        }
    }

    List<ProfileHistory> getChildClients(String profile) {
        CommonTableExpression<Record5<String, Long, Boolean, String, LocalDateTime>> childClients = getChildClientCte(profile);
        return dslContext.withRecursive(childClients)
            .selectFrom(childClients)
            .orderBy(childClients.field(PROFILE_HISTORY.UPDATE_DATE.getName()))
            .fetchInto(ProfileHistory.class);
    }

    ProfileHistory getMasterClients(String profile) {
        CommonTableExpression<Record5<String, Long, Boolean, String, LocalDateTime>> parentClient = getMasterClientCte(profile);
        return dslContext.withRecursive(parentClient)
            .selectFrom(parentClient)
            .orderBy(parentClient.field(PROFILE_HISTORY.PARENT_PROFILE).isNull())
            .fetchOneInto(ProfileHistory.class);
    }

    private void mergeClients(ProfileChange profileChange) {
        int masterFlag = formClient(
            new ProfileHistory(
                profileChange.masterProfile(),
                profileChange.masterProfileVersion(),
                true,
                null,
                LocalDateTime.now())
        );
        if (masterFlag == 0) {
            logger.warn(
                String.format("MERGE unsuccessful, current version is more recent: %s/%s",
                    profileChange.masterProfile(), profileChange.masterProfileVersion())
            );
        }
        int deactivatedFlag = formClient(
            new ProfileHistory(
                profileChange.deactivatedProfileMerge(),
                profileChange.deactivatedProfileMergeVersion(),
                false,
                profileChange.masterProfile(),
                LocalDateTime.now())
        );
        if (deactivatedFlag == 0) {
            logger.warn(
                String.format("MERGE unsuccessful, current version is more recent: %s/%s",
                    profileChange.deactivatedProfileMerge(), profileChange.deactivatedProfileMergeVersion())
            );
        }
    }

    private void unmergeClients(ProfileChange profileChange) {
        int masterFlag = formClient(
            new ProfileHistory(
                profileChange.deactivatedProfileUnmerge(),
                profileChange.deactivatedProfileUnmergeVersion(),
                false,
                null,
                LocalDateTime.now())
        );
        if (masterFlag == 0) {
            logger.warn(
                String.format("UNMERGE unsuccessful, current version is more recent: %s/%s",
                    profileChange.deactivatedProfileUnmerge(), profileChange.deactivatedProfileUnmergeVersion())
            );
        }
        int deactivatedFlag = formClient(
            new ProfileHistory(
                profileChange.activatedProfile(),
                profileChange.activatedProfileVersion(),
                true,
                null,
                LocalDateTime.now())
        );
        if (deactivatedFlag == 0) {
            logger.warn(
                String.format("UNMERGE unsuccessful, current version is more recent: %s/%s",
                    profileChange.activatedProfile(), profileChange.activatedProfileVersion())
            );
        }
        copyHistoricalClient(profileChange);
    }

    private void copyHistoricalClient(ProfileChange profileChange) {
        dslContext.insertInto(
                PROFILE_HISTORY,
                PROFILE_HISTORY.PROFILE,
                PROFILE_HISTORY.PROFILE_VERSION,
                PROFILE_HISTORY.IS_ACTIVE,
                PROFILE_HISTORY.UPDATE_DATE
            )
            .values(
                profileChange.newProfile(),
                NEW_CLIENT_PROFILE,
                true,
                LocalDateTime.now()
            )
            .onConflictDoNothing()
            .execute();
    }

    private int formClient(ProfileHistory profileHistory) {
        return dslContext.insertInto(
                PROFILE_HISTORY,
                PROFILE_HISTORY.PROFILE,
                PROFILE_HISTORY.PROFILE_VERSION,
                PROFILE_HISTORY.IS_ACTIVE,
                PROFILE_HISTORY.PARENT_PROFILE,
                PROFILE_HISTORY.UPDATE_DATE
            )
            .values(
                profileHistory.profile(),
                profileHistory.profileVersion(),
                profileHistory.isActive(),
                profileHistory.parentProfile(),
                profileHistory.updateDate()
            )
            .onConflict()
            .doUpdate()
            .set(PROFILE_HISTORY.PROFILE_VERSION, profileHistory.profileVersion())
            .set(PROFILE_HISTORY.IS_ACTIVE, profileHistory.isActive())
            .set(PROFILE_HISTORY.UPDATE_DATE, profileHistory.updateDate())
            .set(PROFILE_HISTORY.PARENT_PROFILE, profileHistory.parentProfile())
            .where(PROFILE_HISTORY.PROFILE_VERSION.lessThan(profileHistory.profileVersion()))
            .execute();
    }

}
