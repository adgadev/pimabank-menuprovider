package com.grexdev.pimabank.menuprovider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.RegexParserMenuProvider;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptorProvider;

@RequiredArgsConstructor
public class MenuProviderFactory {
    
    public interface RestaurantDescriptorLocation {
        String getRestaurantDescriptorLocation();
    }

    @Getter
    public enum Restaurant implements RestaurantDescriptorLocation {
        PEPERONE("peperone-descriptor.xml"),
        PHUONG_DONG("phuong-dong-descriptor.xml");
        
        private String restaurantDescriptorLocation;
        
        private Restaurant(String restaurantDescriptorLocation) {
            this.restaurantDescriptorLocation = restaurantDescriptorLocation;
        }
    };
    
    private final MenuProviderFactoryConfiguration configuration;
    
    private RestaurantDescriptorProvider provider = new RestaurantDescriptorProvider();

    public MenuProvider createMenuProvider(RestaurantDescriptorLocation restaurant) throws MenuProviderException {
        if (restaurant != null) {
            RestaurantDescriptor restaurantDescriptor = provider.getRestaurantDescriptor(restaurant.getRestaurantDescriptorLocation());
            return new RegexParserMenuProvider(configuration.getMenuProviderConfiguration(), restaurantDescriptor);
        } else {
            throw new IllegalArgumentException("Invalid restaurants descriptor location");
        }
    }

}
