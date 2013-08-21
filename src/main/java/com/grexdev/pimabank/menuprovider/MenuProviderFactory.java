package com.grexdev.pimabank.menuprovider;

import com.grexdev.pimabank.menuprovider.peperone.PeperoneMenuProvider;

public class MenuProviderFactory {

    public enum Restaurant {
        PEPERONE;
    };

    public MenuProvider createMenuProvider(Restaurant restaurant) {
        if (restaurant == Restaurant.PEPERONE) {
            return new PeperoneMenuProvider();
        } else {
            throw new UnsupportedOperationException("Menu provider module doesn't support retrieving menu for " + restaurant);
        }
    }

}
