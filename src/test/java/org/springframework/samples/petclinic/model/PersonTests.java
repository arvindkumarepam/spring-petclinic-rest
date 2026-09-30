package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the Person model, specifically testing the newly added email field.
 * These tests validate the getter and setter functionality for the email field.
 * 
 * @author Test Automation Team
 * @related EPMCDMEST-67208
 */
@DisplayName("Person Email Field Tests")
class PersonTests {

    /**
     * Test case to verify that the email field can be set and retrieved correctly.
     * This test creates a Vet (which extends Person), sets an email, and verifies
     * it can be retrieved using the getter method.
     */
    @Test
    @DisplayName("Should set and get email correctly")
    void testSetAndGetEmail() {
        // Arrange
        Vet vet = new Vet();
        String expectedEmail = "john.doe@petclinic.com";

        // Act
        vet.setEmail(expectedEmail);

        // Assert
        assertThat(vet.getEmail()).isEqualTo(expectedEmail);
    }

    /**
     * Test case to verify that the email field can be set to null.
     * This ensures that the email field is optional (backward compatibility).
     */
    @Test
    @DisplayName("Should allow null email")
    void testNullEmail() {
        // Arrange
        Vet vet = new Vet();

        // Act
        vet.setEmail(null);

        // Assert
        assertThat(vet.getEmail()).isNull();
    }

    /**
     * Test case to verify that the email field is null by default when not set.
     * This ensures backward compatibility with existing code.
     */
    @Test
    @DisplayName("Should have null email by default")
    void testDefaultEmailIsNull() {
        // Arrange & Act
        Vet vet = new Vet();

        // Assert
        assertThat(vet.getEmail()).isNull();
    }

    /**
     * Test case to verify that the email field can be updated.
     * This ensures that the email can be modified after initial setting.
     */
    @Test
    @DisplayName("Should update email successfully")
    void testUpdateEmail() {
        // Arrange
        Vet vet = new Vet();
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";

        // Act
        vet.setEmail(oldEmail);
        assertThat(vet.getEmail()).isEqualTo(oldEmail);

        vet.setEmail(newEmail);

        // Assert
        assertThat(vet.getEmail()).isEqualTo(newEmail);
        assertThat(vet.getEmail()).isNotEqualTo(oldEmail);
    }

    /**
     * Test case to verify that an empty string can be set as an email.
     * This tests edge case behavior.
     */
    @Test
    @DisplayName("Should allow empty string as email")
    void testEmptyStringEmail() {
        // Arrange
        Vet vet = new Vet();

        // Act
        vet.setEmail("");

        // Assert
        assertThat(vet.getEmail()).isEqualTo("");
        assertThat(vet.getEmail()).isNotNull();
    }

    /**
     * Test case to verify that the email field works with Owner entity (another Person subclass).
     * This ensures that the email field inheritance works correctly.
     */
    @Test
    @DisplayName("Should work with Owner entity (Person subclass)")
    void testEmailWithOwner() {
        // Arrange
        Owner owner = new Owner();
        String expectedEmail = "owner@example.com";

        // Act
        owner.setEmail(expectedEmail);

        // Assert
        assertThat(owner.getEmail()).isEqualTo(expectedEmail);
    }

    /**
     * Test case to verify that different Person instances have independent email values.
     * This ensures that there are no static field issues.
     */
    @Test
    @DisplayName("Should maintain independent emails for different instances")
    void testIndependentEmails() {
        // Arrange
        Vet vet1 = new Vet();
        Vet vet2 = new Vet();
        String email1 = "vet1@petclinic.com";
        String email2 = "vet2@petclinic.com";

        // Act
        vet1.setEmail(email1);
        vet2.setEmail(email2);

        // Assert
        assertThat(vet1.getEmail()).isEqualTo(email1);
        assertThat(vet2.getEmail()).isEqualTo(email2);
        assertThat(vet1.getEmail()).isNotEqualTo(vet2.getEmail());
    }

    /**
     * Test case to verify that long email addresses can be stored.
     * This tests the field's capacity to handle longer email strings.
     */
    @Test
    @DisplayName("Should handle long email addresses")
    void testLongEmail() {
        // Arrange
        Vet vet = new Vet();
        // Create a long but valid email (within 255 character limit)
        String longEmail = "very.long.email.address.with.multiple.dots.and.subdomains@very.long.domain.name.with.multiple.subdomains.petclinic.com";

        // Act
        vet.setEmail(longEmail);

        // Assert
        assertThat(vet.getEmail()).isEqualTo(longEmail);
        assertThat(vet.getEmail().length()).isLessThanOrEqualTo(255); // DB column limit
    }

    /**
     * Test case to verify that special characters in email addresses can be stored.
     * This ensures the field handles valid email formats with special characters.
     */
    @Test
    @DisplayName("Should handle special characters in email")
    void testSpecialCharactersInEmail() {
        // Arrange
        Vet vet = new Vet();
        String specialEmail = "user+name.test-_vet123@sub-domain_123.petclinic.co.uk";

        // Act
        vet.setEmail(specialEmail);

        // Assert
        assertThat(vet.getEmail()).isEqualTo(specialEmail);
    }
}
