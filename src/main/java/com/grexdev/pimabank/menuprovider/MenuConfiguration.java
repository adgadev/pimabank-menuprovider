package com.grexdev.pimabank.menuprovider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MenuConfiguration {
    
    private final String restaurantPageBaseUrl;
   
    /**
     * pageName is mainly for testing
     */
    private String pageName;
    
}