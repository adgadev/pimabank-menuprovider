package com.grexdev.pimabank.menuprovider;

import lombok.RequiredArgsConstructor;

import com.grexdev.pimabank.menuprovider.peperone.MenuProviderConfiguration;
import com.grexdev.pimabank.menuprovider.peperone.PeperoneMenuProvider;

@RequiredArgsConstructor
public class MenuProviderFactory {

    public enum Restaurant {
        PEPERONE;
    };
    
    private final MenuProviderFactoryConfiguration configuration;

    public MenuProvider createMenuProvider(Restaurant restaurant) {
        if (restaurant == Restaurant.PEPERONE) {
            MenuProviderConfiguration providerConfiguration = new MenuProviderConfiguration(configuration.getPeperoneBaseUrl());
            return new PeperoneMenuProvider(providerConfiguration);
        } else {
            throw new UnsupportedOperationException("Menu provider module doesn't support retrieving menu for " + restaurant);
        }
    }

}
