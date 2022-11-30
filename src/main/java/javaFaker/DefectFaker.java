package javaFaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import entity.Defect;

import java.util.Locale;

public class DefectFaker {
    Faker faker;

    public Defect getDefect() {
        faker = new Faker(new Locale("en-GB"), new RandomService());
        String severity = faker.options().option("Not set", "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial");

        return Defect.builder()
                .withDefectTitle(faker.artist().name())
                .withActualResult(faker.book().author())
                .withSeverity(severity).create();
    }
}
