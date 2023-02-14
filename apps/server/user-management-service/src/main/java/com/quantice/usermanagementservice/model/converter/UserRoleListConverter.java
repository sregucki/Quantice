package com.quantice.usermanagementservice.model.converter;

import com.quantice.usermanagementservice.model.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class UserRoleListConverter implements AttributeConverter<List<UserRole>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(final List<UserRole> attribute) {

        return attribute != null ? String.join(SPLIT_CHAR, attribute.stream().map(Enum::name).toList()) : "";
    }

    @Override
    public List<UserRole> convertToEntityAttribute(final String dbData) {

        return dbData != null ? Arrays.stream(dbData.split(SPLIT_CHAR)).map(UserRole::valueOf).toList() : emptyList();
    }
}
