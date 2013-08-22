package com.grexdev.pimabank.menuprovider.peperone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MenuProviderConfiguration {
    
    private final String restaurantPageBaseUrl;
   
    /**
     * pageName is mainly for testing
     */
    private String pageName;
    
}