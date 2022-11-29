package javaFaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import entity.Case;

import java.util.Locale;

public class CaseFaker {
    Faker faker;

    public Case getCase() {
        faker = new Faker( new Locale("en-GB"), new RandomService());
        String status = faker.options().option("Actual", "Draft", "Deprecated");
        String severity = faker.options().option("Not set", "Blocker", "Critical", "Major", "Normal", "Minor", "Trivial");
        String priority = faker.options().option("Not set", "High", "Medium", "Low");
        String type = faker.options().option("Other", "Functional", "Smoke", "Regression", "Security", "Usability", "Performance", "Acceptance",
                "Compatibility", "Integration", "Exploratory");
        String layer = faker.options().option("Not set", "E2E", "API", "Unit");
        String isFlaky = faker.options().option("No", "Yes");
        String behavior = faker.options().option("Not set", "Positive", "Negative", "Destructive");
        String automationStatus = faker.options().option("Not automated", "To be automated", "Automated");

        return Case.builder()
                .withTitle(faker.country().name())
                .withStatus(status)
                .withDescription(faker.regexify("[A-z1-9]{10}"))
                .withSeverity(severity)
                .withPriority(priority)
                .withType(type)
                .withLayer(layer)
                .withIsFlaky(isFlaky)
                .withBehavior(behavior)
                .withAutomationStatus(automationStatus)
                .withPreconditions(faker.regexify("[A-z1-9]{15}"))
                .withPostconditions(faker.regexify("[A-z1-9]{15}"))
                .create();
    }
}
