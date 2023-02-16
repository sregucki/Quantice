package com.quantice.usermanagementservice.model.converter;

import com.quantice.usermanagementservice.model.enums.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class RoleListConverter implements AttributeConverter<List<RoleType>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(final List<RoleType> attribute) {

        return attribute != null ? String.join(SPLIT_CHAR, attribute.stream().map(Enum::name).toList()) : "";
    }

    @Override
    public List<RoleType> convertToEntityAttribute(final String dbData) {

        return dbData != null ? Arrays.stream(dbData.split(SPLIT_CHAR)).map(RoleType::valueOf).toList() : emptyList();
    }
}
