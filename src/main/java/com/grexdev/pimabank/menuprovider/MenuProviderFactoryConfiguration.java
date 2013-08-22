package com.grexdev.pimabank.menuprovider;

import lombok.Getter;

public class MenuProviderFactoryConfiguration {
    
    @Getter
    private String peperoneBaseUrl;
    
    public static MenuProviderFactoryConfiguration getDefaultConfiguration() {
        MenuProviderFactoryConfiguration configuration = new MenuProviderFactoryConfiguration();
        configuration.peperoneBaseUrl = "http://peperone.pl/";
        return configuration;
    }

}
