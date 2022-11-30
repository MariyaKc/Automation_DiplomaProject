package javaFaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import entity.Milestone;

import java.util.Locale;

public class MilestoneFaker {

    Faker faker;

    public Milestone getMilestone() {
        faker = new Faker(new Locale("en-GB"), new RandomService());
        String status = faker.options().option("Active", "Completed");

        return Milestone.builder()
                .withMilestoneName(faker.aviation().airport())
                .withDescription(faker.regexify("[A-z1-9]{15}"))
                .withStatus(status)
                .withDueDate(faker.regexify("(((20[012]\\d|19\\d\\d)|(1\\d|2[0123]))-((0[0-9])|(1[012]))-((0[1-9])|([12][0-9])|(3[01])))")).create();
    }
}

