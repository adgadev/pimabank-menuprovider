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
        super("phuong-dong-descriptor.xml", "phuong-dong/www.bar-orientalny.pl/pl");
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
            new MenuPosition(1, "KURCZAK PO WIETNAMSKU (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(2, "KURCZAK PO CHIŃSKU (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(3, "KURCZAK W CIEŚCIE KOKOSOWYM", new BigDecimal("15.50"), null),
            new MenuPosition(4, "KURCZAK\"GONG-BAO\"", new BigDecimal("15.50"), null),
            new MenuPosition(5, "KURCZAK Z BAMBUSEM I GRZYBAMI", new BigDecimal("15.50"), null),
            new MenuPosition(6, "KURCZAK Z WARZYWAMI", new BigDecimal("15.00"), null),
            new MenuPosition(7, "KURCZAK SŁODKO-KWAŚNY", new BigDecimal("15.00"), null),
            new MenuPosition(8, "KURCZAK W SOSIE CURRY", new BigDecimal("15.00"), null),
            new MenuPosition(9, "KURCZAK W SOSIE PIKANTNYM", new BigDecimal("15.00"), null),
            new MenuPosition(10, "KURCZAK PIĘCIU SMAKÓW", new BigDecimal("15.00"), null),
            new MenuPosition(11, "KURCZAK CHRUPIĄCY (na gorącym półmisku)", new BigDecimal("15.50"), null),
            new MenuPosition(12, "KURCZAK DUSZONY Z PACHNĄCYMI GRZYBAMI", new BigDecimal("15.50"), null),
            new MenuPosition(13, "FILET Z KURCZAKA W PANIERCE (na gorącym półmisku)", new BigDecimal("15.50"), null),
            new MenuPosition(14, "FILET Z KURCZAKA PANIEROWANY", new BigDecimal("15.50"), null),
            new MenuPosition(15, "KURCZAK PIKANTNY Z CZOSNKIEM (na gorącym półmisku)", new BigDecimal("16.00"), null),
            new MenuPosition(16, "KURCZAK Z BROKUŁAMI I CZOSNKIEM", new BigDecimal("15.50"), null),
            new MenuPosition(17, "KURCZAK Z ANANASEM I CEBULĄ", new BigDecimal("15.50"), null),
            new MenuPosition(18, "KURCZAK W CIEŚCIE SEZAMOWYM", new BigDecimal("15.50"), null),
            new MenuPosition(19, "KURCZAK PO SECZUAŃSKU", new BigDecimal("15.50"), null),
            new MenuPosition(20, "KURCZAK Z PIECZARKAMI", new BigDecimal("15.50"), null),
            // TODO: fix space
            new MenuPosition(21, "KURCZAK W SOSIEWINNYM", new BigDecimal("15.50"), null) };

    private MenuPosition[] napojezimneMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "COCA COLA, COCA COLA LIGHT", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(2, "COCA COLA", "0,50 l", new BigDecimal("5.50"), null),
            new MenuPosition(3, "COCA COLA", "1,00 l", new BigDecimal("7.00"), null),
            new MenuPosition(4, "FANTA", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(5, "SPRITE", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(6, "KINLEY", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(7, "NESTEA", "0,50 l", new BigDecimal("5.50"), null),
            new MenuPosition(8, "SOKI", "0,20 l", new BigDecimal("3.50"), null),
            new MenuPosition(9, "KROPLA BESKIDU (gazowana, niegazowana)", "0,25 l", new BigDecimal("3.50"), null),
            new MenuPosition(10, "PIWO", "0,33 l", new BigDecimal("5.00"), null),
            new MenuPosition(11, "PIWO", "0,50 l", new BigDecimal("6.00"), null) };
}
