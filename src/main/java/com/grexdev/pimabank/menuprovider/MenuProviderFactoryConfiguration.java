package com.grexdev.pimabank.menuprovider;

import lombok.Getter;
import lombok.Setter;

import com.grexdev.pimabank.menuprovider.parser.RegexParserMenuProviderConfiguration;

@Getter
@Setter
public class MenuProviderFactoryConfiguration {
    
    private RegexParserMenuProviderConfiguration menuProviderConfiguration;
       
    public static MenuProviderFactoryConfiguration getDefaultConfiguration() {
        MenuProviderFactoryConfiguration configuration = new MenuProviderFactoryConfiguration();
        configuration.menuProviderConfiguration = RegexParserMenuProviderConfiguration.getDefault();
        return configuration;
    }

}
