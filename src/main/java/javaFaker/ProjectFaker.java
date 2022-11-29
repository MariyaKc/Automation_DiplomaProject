package javaFaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import entity.Project;

import java.util.Locale;

public class ProjectFaker {
        Faker faker;

        public Project getProject() {

            faker = new Faker( new Locale("en-GB"), new RandomService());
            String accessType = faker.options().option("private", "public");

            return Project.builder()
                    .withTitle(faker.app().name())
                    .withCode(faker.regexify("[A-Z]{5}"))
                    .withDescription(faker.regexify("[a-z1-9]{10}"))
                    .withAccess(accessType)
                    .create();
        }
    }

