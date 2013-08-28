package com.grexdev.pimabank.menuprovider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptorProvider;
import com.grexdev.pimabank.menuprovider.peperone.MenuProviderConfiguration;
import com.grexdev.pimabank.menuprovider.peperone.PeperoneMenuProvider;

@RequiredArgsConstructor
public class MenuProviderFactory {
    
    public interface RestaurantDescriptorLocation {
        String getRestaurantDescriptorLocation();
    }

    @Getter
    public enum Restaurant implements RestaurantDescriptorLocation {
        PEPERONE("peperone-descriptor.xml");
        
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
            MenuProviderConfiguration providerConfiguration = new MenuProviderConfiguration();
            return new PeperoneMenuProvider(providerConfiguration, restaurantDescriptor);
        } else {
            throw new IllegalArgumentException("Invalid restaurants descriptor location");
        }
    }

}
