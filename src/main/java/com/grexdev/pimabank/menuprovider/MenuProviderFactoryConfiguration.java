package com.grexdev.pimabank.menuprovider;

import lombok.Getter;
import lombok.Setter;

import com.grexdev.pimabank.menuprovider.peperone.MenuProviderConfiguration;

@Getter
@Setter
public class MenuProviderFactoryConfiguration {
    
    private MenuProviderConfiguration menuProviderConfiguration;
       
    public static MenuProviderFactoryConfiguration getDefaultConfiguration() {
        MenuProviderFactoryConfiguration configuration = new MenuProviderFactoryConfiguration();
        configuration.menuProviderConfiguration = MenuProviderConfiguration.getDefault();
        return configuration;
    }

}
