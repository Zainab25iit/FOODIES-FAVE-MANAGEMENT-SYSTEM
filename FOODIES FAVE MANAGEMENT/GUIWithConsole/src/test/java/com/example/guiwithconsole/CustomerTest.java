package com.example.guiwithconsole;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("John", "Doe", 3);
    }

    @Test
    public void testGetFullName() {
        String fullName = customer.getFullName();
        Assertions.assertEquals("John Doe", fullName);
    }

    @Test
    public void testGetBurgersRequired() {
        int burgersRequired = customer.getBurgersRequired();
        Assertions.assertEquals(3, burgersRequired);
    }

    @Test
    public void testFirstNameProperty() {
        StringProperty firstNameProperty = customer.firstNameProperty();
        Assertions.assertEquals("John", firstNameProperty.get());
    }

    @Test
    public void testLastNameProperty() {
        StringProperty lastNameProperty = customer.lastNameProperty();
        Assertions.assertEquals("Doe", lastNameProperty.get());
    }

    @Test
    public void testBurgersRequiredProperty() {
        IntegerProperty burgersRequiredProperty = customer.burgersRequiredProperty();
        Assertions.assertEquals(3, burgersRequiredProperty.get());
    }
}
