package com.grexdev.pimabank.menuprovider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MenuConfiguration {
    
    private final String restaurantPageBaseUrl;
    
    private String pageName;
    
}