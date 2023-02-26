package com.quantice.authservice.util;

import com.quantice.authservice.model.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class UserRoleConverter implements AttributeConverter<List<Role>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(final List<Role> attribute) {

        return attribute != null ? String.join(SPLIT_CHAR, attribute.stream().map(Enum::name).toList()) : "";
    }

    @Override
    public List<Role> convertToEntityAttribute(final String roles) {

        return roles != null ? Arrays.stream(roles.split(SPLIT_CHAR)).map(Role::valueOf).toList() : emptyList();
    }
}
