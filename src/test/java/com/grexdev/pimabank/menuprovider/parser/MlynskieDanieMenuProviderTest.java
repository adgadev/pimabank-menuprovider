package com.grexdev.pimabank.menuprovider.parser;

import java.math.BigDecimal;
import java.util.List;

import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;

public class MlynskieDanieMenuProviderTest extends AbstractMenuProviderTest {

    public MlynskieDanieMenuProviderTest() {
        super("mlynskie-danie-descriptor.xml", "mlynskie-danie/www.mlynskiedanie.pl");
    }

    @Test
    public void shouldParseDaniaJarskiePage() throws MenuProviderException {
        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("dania-jarskie");
        RegexParserMenuProvider peperoneMenuProvider = new RegexParserMenuProvider(getConfiguration(), restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "DANIA JARSKIE", daniajarskieMenuPositions);
    }

    private MenuPosition[] daniajarskieMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Knedle ze śliwkami", new BigDecimal("11.50"), null),
            new MenuPosition(2, "Kopytka z sosem pieczarkowym", new BigDecimal("10.50"), null),
            new MenuPosition(3, "Kluski Śląskie z sosem pieczarkowym", new BigDecimal("10.50"), null),
            new MenuPosition(4, "Łazanki z kapustą", new BigDecimal("10.50"), null),
            new MenuPosition(5, "Krokiety z kapustą i grzybami", new BigDecimal("8.50"), null),
            new MenuPosition(6, "Naleśniki z dżemem", new BigDecimal("8.50"), null),
            new MenuPosition(7, "Naleśniki z serem", new BigDecimal("8.50"), null),
            new MenuPosition(8, "Naleśniki ze szpinakiem w sosie szpinakowym", new BigDecimal("8.50"), null) };

}