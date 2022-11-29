package javaFaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import entity.Suite;

import java.util.Locale;

public class SuiteFaker {
    Faker faker;

    public Suite getSuite() {
        faker = new Faker( new Locale("en-GB"), new RandomService());

        return Suite.builder()
                .withSuiteName(faker.animal().name())
                .withDescription(faker.regexify("[a-z1-9]{10}"))
                .withPreconditions(faker.regexify("[a-z1-9]{10}"))
                .create();
    }
}
