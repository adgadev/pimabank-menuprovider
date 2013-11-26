package com.grexdev.pimabank.menuprovider.parser;

import java.math.BigDecimal;
import java.util.List;

import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;

public class PhuongDongMenuProviderTest extends AbstractMenuProviderTest {

    public PhuongDongMenuProviderTest() {
        super("phuong-dong-descriptor.xml", "phuong-dong/www.bar-orientalny.pl/pl/lubelska-10/menu");
    }

    @Test
    public void shouldParseDanieZKurczakaPage() throws MenuProviderException {
        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("dania-z-kurczaka");
        RegexParserMenuProvider peperoneMenuProvider = new RegexParserMenuProvider(getConfiguration(), restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Dania z kurczaka z ryżem i surówką", daniazkurczakaMenuPositions);
    }

    @Test
    public void shouldParseNapojeZimnePage() throws MenuProviderException {
        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("napoje-zimne");
        RegexParserMenuProvider peperoneMenuProvider = new RegexParserMenuProvider(getConfiguration(), restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Napoje zimne", napojezimneMenuPositions);
    }

    private MenuPosition[] daniazkurczakaMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Opakowanie (do dań na wynos i z dostawą)", new BigDecimal("1.00"), null),
            new MenuPosition(2, "Kurczak po wietnamsku (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(3, "Kurczak po chińsku (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(4, "Kurczak w cieście kokosowym", new BigDecimal("15.50"), null),
            new MenuPosition(5, "Kurczak \"Gong-Bao\"", new BigDecimal("15.50"), null),
            new MenuPosition(6, "Kurczak z bambusem i grzybami", new BigDecimal("15.50"), null),
            new MenuPosition(7, "Kurczak z warzywami", new BigDecimal("15.00"), null),
            new MenuPosition(8, "Kurczak słodko-kwaśny", new BigDecimal("15.00"), null),
            new MenuPosition(9, "Kurczak w sosie curry", new BigDecimal("15.00"), null),
            new MenuPosition(10, "Kurczak w sosie pikantnym", new BigDecimal("15.00"), null),
            new MenuPosition(11, "Kurczak pięciu smaków", new BigDecimal("15.00"), null),
            new MenuPosition(12, "Kurczak chrupiący (na gorącym półmisku)", new BigDecimal("15.50"), null),
            new MenuPosition(13, "Kurczak duszony z pachnącymi grzybami", new BigDecimal("15.50"), null),
            new MenuPosition(14, "Filet z kurczaka w panierce (na gorącym półmisku)", new BigDecimal("15.50"), null),
            new MenuPosition(15, "Filet z kurczaka panierowany", new BigDecimal("15.50"), null),
            new MenuPosition(16, "Kurczak pikantny z czosnkiem (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(17, "Kurczak z brokułami i czosnkiem", new BigDecimal("15.50"), null),
            new MenuPosition(18, "Kurczak z ananasem i cebulą", new BigDecimal("15.50"), null),
            new MenuPosition(19, "Kurczak w cieście sezamowym", new BigDecimal("15.50"), null),
            new MenuPosition(20, "Kurczak po seczuańsku", new BigDecimal("15.50"), null),
            new MenuPosition(21, "Kurczak z pieczarkami", new BigDecimal("15.50"), null),
            new MenuPosition(22, "Kurczak w sosiewinnym", new BigDecimal("15.50"), null) };

    private MenuPosition[] napojezimneMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Coca Cola, Coca Cola Light", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(2, "Coca Cola", "0,50 l", new BigDecimal("5.50"), null),
            new MenuPosition(3, "Coca Cola", "1,00 l", new BigDecimal("7.00"), null),
            new MenuPosition(4, "Fanta", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(5, "Sprite", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(6, "Kinley", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(7, "Nestea", "0,50 l", new BigDecimal("5.50"), null),
            new MenuPosition(8, "Soki", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(9, "Kropla beskidu (gazowana, niegazowana)", "0,25 l", new BigDecimal("3.50"), null) };
}
