package com.quantice.usermanagementservice.config.data;

import com.quantice.usermanagementservice.model.user.User;
import com.quantice.usermanagementservice.model.utils.enums.RoleType;
import com.quantice.usermanagementservice.repository.RoleRepository;
import com.quantice.usermanagementservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DataInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    @ConditionalOnProperty(name = "initializeSampleUserData", havingValue = "true")
    public CommandLineRunner initSampleData(UserRepository userRepository, RoleRepository roleRepository) {

        if (userRepository.count() > 0) {
            LOGGER.warn("Sample users hasn't been saved - they're already in database");
            return null;
        }

        List<String> sampleUsernames = List.of("Arianna Guerrero", "Charis Krueger", "Tanya Lee", "Dawn Wilkerson",
            "Lucille Sheppard", "Conner Ford", "Alma Pineda", "Dominic Douglas", "Raymond Bradshaw", "Rex Holder");
        List<String> sampleEmails = List.of("chrisk@att.net", "fatelk@yahoo.ca", "ideguy@aol.com",
            "dodong@msn.com", "jaxweb@me.com", "onestab@verizon.net", "studyabr@aol.com", "brickbat@yahoo.ca",
            "oneiros@gmail.com", "jhardin@verizon.net", "vsprintf@att.net", "dmbkiwi@icloud.com");

        List<User> sampleUsers = zip(List.of(sampleUsernames, sampleEmails))
            .stream()
            .map(userData -> User.builder()
                .username(userData.get(0))
                .email(userData.get(1))
                    .userRoleIdFk(roleRepository.findRoleByRoleType(RoleType.ROLE_USER).getRoleId())
                    .roles(List.of(roleRepository.findRoleByRoleType(RoleType.ROLE_USER)))
                .build())
            .toList();

        return (args -> {
            LOGGER.info("Sample users saved!");
            userRepository.saveAll(sampleUsers);
        });

    }

    static <T> List<List<T>> zip(List<List<T>> listOfLists) {
        int size = listOfLists.get(0).size();
        List<List<T>> result = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            final int finalI = i;
            result.add(
                listOfLists.stream()
                    .map(list -> list.get(finalI))
                    .collect(toList()));
        }
        return result;
    }

}
