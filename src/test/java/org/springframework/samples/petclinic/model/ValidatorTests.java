package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldNotValidateWhenFirstNameEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    /**
     * Test case for validating a valid email address.
     * Expected result: No validation errors should occur.
     */
    @Test
    void shouldValidateValidEmail() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@example.com");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations).isEmpty();
    }

    /**
     * Test case for validating an invalid email address (no @ symbol).
     * Expected result: One validation error should occur for the email field.
     */
    @Test
    void shouldRejectInvalidEmailWithoutAtSymbol() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("invalid-email");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
    }

    /**
     * Test case for validating an invalid email address (missing domain).
     * Expected result: One validation error should occur for the email field.
     */
    @Test
    void shouldRejectInvalidEmailWithoutDomain() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("user@");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
    }

    /**
     * Test case for validating a null email address.
     * Expected result: No validation errors (email is optional).
     */
    @Test
    void shouldAllowNullEmail() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail(null);

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations).isEmpty();
    }

    /**
     * Test case for validating an empty string email address.
     * Expected result: No validation errors (empty string is treated as valid by @Email).
     */
    @Test
    void shouldAllowEmptyEmail() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations).isEmpty();
    }

    /**
     * Test case for validating an email with special characters.
     * Expected result: No validation errors (special characters are valid in emails).
     */
    @Test
    void shouldValidateEmailWithSpecialCharacters() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe+test@example.com");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations).isEmpty();
    }

    /**
     * Test case for validating an email with subdomain.
     * Expected result: No validation errors.
     */
    @Test
    void shouldValidateEmailWithSubdomain() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("admin@mail.petclinic.com");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations).isEmpty();
    }
}
