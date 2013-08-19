package com.grexdev.pimabank.menuprovider;

import java.util.List;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;

public interface MenuProvider {
    
    List<MenuPage> fetchRestaurantMenu(MenuConfiguration configuration) throws MenuProviderException;
}
