/*
 * This file is generated by jOOQ.
 */
package jooq.tables;


import jooq.Keys;
import jooq.Profile;
import jooq.tables.records.ProfileHistoryRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Collection;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ProfileHistory extends TableImpl<ProfileHistoryRecord> {

    /**
     * The reference instance of <code>profile.profile_history</code>
     */
    public static final ProfileHistory PROFILE_HISTORY = new ProfileHistory();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>profile.profile_history.profile</code>.
     */
    public final TableField<ProfileHistoryRecord, String> PROFILE = createField(DSL.name("profile"), SQLDataType.VARCHAR.nullable(false), this, "");
    /**
     * The column <code>profile.profile_history.profile_version</code>.
     */
    public final TableField<ProfileHistoryRecord, Long> PROFILE_VERSION = createField(DSL.name("profile_version"), SQLDataType.BIGINT.nullable(false), this, "");
    /**
     * The column <code>profile.profile_history.is_active</code>.
     */
    public final TableField<ProfileHistoryRecord, Boolean> IS_ACTIVE = createField(DSL.name("is_active"), SQLDataType.BOOLEAN.nullable(false), this, "");
    /**
     * The column <code>profile.profile_history.parent_profile</code>.
     */
    public final TableField<ProfileHistoryRecord, String> PARENT_PROFILE = createField(DSL.name("parent_profile"), SQLDataType.VARCHAR, this, "");
    /**
     * The column <code>profile.profile_history.update_date</code>.
     */
    public final TableField<ProfileHistoryRecord, LocalDateTime> UPDATE_DATE = createField(DSL.name("update_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    private ProfileHistory(Name alias, Table<ProfileHistoryRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProfileHistory(Name alias, Table<ProfileHistoryRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>profile.profile_history</code> table reference
     */
    public ProfileHistory(String alias) {
        this(DSL.name(alias), PROFILE_HISTORY);
    }

    /**
     * Create an aliased <code>profile.profile_history</code> table reference
     */
    public ProfileHistory(Name alias) {
        this(alias, PROFILE_HISTORY);
    }

    /**
     * Create a <code>profile.profile_history</code> table reference
     */
    public ProfileHistory() {
        this(DSL.name("profile_history"), null);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileHistoryRecord> getRecordType() {
        return ProfileHistoryRecord.class;
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Profile.PROFILE;
    }

    @Override
    public UniqueKey<ProfileHistoryRecord> getPrimaryKey() {
        return Keys.PROFILE_HISTORY_PK;
    }

    @Override
    public ProfileHistory as(String alias) {
        return new ProfileHistory(DSL.name(alias), this);
    }

    @Override
    public ProfileHistory as(Name alias) {
        return new ProfileHistory(alias, this);
    }

    @Override
    public ProfileHistory as(Table<?> alias) {
        return new ProfileHistory(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileHistory rename(String name) {
        return new ProfileHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileHistory rename(Name name) {
        return new ProfileHistory(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileHistory rename(Table<?> name) {
        return new ProfileHistory(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory where(Condition condition) {
        return new ProfileHistory(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProfileHistory where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProfileHistory where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProfileHistory where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProfileHistory where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProfileHistory whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
