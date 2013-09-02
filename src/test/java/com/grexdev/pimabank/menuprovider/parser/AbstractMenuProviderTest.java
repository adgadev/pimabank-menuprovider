package com.grexdev.pimabank.menuprovider.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.MenuPageDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptorProvider;

public class AbstractMenuProviderTest {
    
    private static final int SERVER_PORT = 18723;

    private HttpPageFromClasspathServer server = new HttpPageFromClasspathServer("peperone/peperone.pl-thursday");

    private RegexParserMenuProviderConfiguration configuration = RegexParserMenuProviderConfiguration.getDefault();
    
    private String restaurantDescriptorResource;

    public AbstractMenuProviderTest(String restaurantDescriptorResource, String httpServerRootResource) {
        this.restaurantDescriptorResource = restaurantDescriptorResource;
        this.server = new HttpPageFromClasspathServer(httpServerRootResource);
    }
    
    @BeforeClass
    public void setUp() throws IOException {
        server.startServer(SERVER_PORT);
    }

    @AfterClass
    public void tearDown() throws IOException {
        server.stopServer();
    }
    
    protected RegexParserMenuProviderConfiguration getConfiguration() {
        return configuration;
    }

    protected RestaurantDescriptor createRestaurantDescriptor(String menuPage) throws MenuProviderException {
        RestaurantDescriptor restaurantDescriptor = new RestaurantDescriptorProvider().getRestaurantDescriptor(restaurantDescriptorResource);
        restaurantDescriptor.setBaseUrl(String.format("http://localhost:%s/", SERVER_PORT)); // without it uses real page
        
        // alter descriptor to use only one page, which matches given name
        for (MenuPageDescriptor menuPageDescriptor : restaurantDescriptor.getMenuPages()) {
            if (menuPageDescriptor.getPageId().equals(menuPage)) {
                restaurantDescriptor.setMenuPages(Arrays.asList(menuPageDescriptor));
                return restaurantDescriptor;
            }
        }
        throw new RuntimeException("Page with given name not found in descriptor");
    }
    
    protected void ensureMenuPositionsAreCorrect(List<MenuPage> menuPages, String expectedCategoryName,
            MenuPosition[] expectedMenuPositions) {
        assertNotNull(menuPages);
        assertEquals(menuPages.size(), 1);
        MenuPage page = menuPages.get(0);

        assertEquals(page.getCategory(), expectedCategoryName);
        assertEquals(page.getMenuPositions().size(), expectedMenuPositions.length);
        assertEquals(page.getMenuPositions().toArray(), expectedMenuPositions);
    }

}
