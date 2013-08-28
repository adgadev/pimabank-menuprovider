package com.grexdev.pimabank.menuprovider.parser.descriptor;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;

public class RestaurantDescriptorProviderTest {

    @Test
    public void shouldProviderRestaurantDescriptor() throws MenuProviderException {
        // given
        RestaurantDescriptorProvider restaurantDescriptorProvider = new RestaurantDescriptorProvider();

        // when
        RestaurantDescriptor descriptor = restaurantDescriptorProvider.getRestaurantDescriptor("test-restaurant-descriptor.xml");

        // then
        assertNotNull(descriptor);
        assertEquals(descriptor.getName(), "Peperone");
        assertEquals(descriptor.getBaseUrl(), "http://peperone.pl/");

        assertNotNull(descriptor.getMenuPages());
        assertEquals(descriptor.getMenuPages().size(), 1);
        MenuPageDescriptor menuPageDescriptor = descriptor.getMenuPages().get(0);

        assertEquals(menuPageDescriptor.getPageName(), "calzone");
        assertEquals(menuPageDescriptor.getUrlSuffix(), "calzone-menu");
        assertEquals(menuPageDescriptor.getParserRegexpResource(), "peperone/generic.regex");
        assertEquals(menuPageDescriptor.getVelocityTemplateResource(), "peperone/generic.vm");
        assertEquals(menuPageDescriptor.getXPathExpression(), "//table[@class='contentpaneopen']//span//node()");
        assertFalse(menuPageDescriptor.isPageDisabled());
    }

}
