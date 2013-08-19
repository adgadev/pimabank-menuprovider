package com.grexdev.pimabank.menuprovider;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.peperone.MenuPageMetadata;
import com.grexdev.pimabank.menuprovider.peperone.PeperoneMenuProvider;

public class PeperoneMenuProviderTest {

    private static final int SERVER_PORT = 18723;

    private HttpPageFromClasspathServer server = new HttpPageFromClasspathServer("peperone/peperone.pl-thursday");

    @BeforeClass
    public void setUp() throws IOException {
        server.startServer(SERVER_PORT);
    }

    @AfterClass
    public void tearDown() throws IOException {
        server.stopServer();
    }

    private MenuConfiguration createMenuConfiguration(String menuPage) {
        return new MenuConfiguration("http://localhost:18723/", menuPage);
      //   return new MenuConfiguration("http://peperone.pl/", menuPage);
    }
    
    @Test
    public void shouldParseMenudniaPage() throws MenuProviderException {
        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.MENUDNIA.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Danie dnia", menudniaMenuPositions);
    }

    @Test
    public void shouldParseCalzonePage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.CALZONE.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Calzone", calzoneMenuPositions);
    }

    @Test
    public void shouldParseDlaDzieciPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.DLADZIECI.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Menu dla dzieci", dlaDzieciMenuPositions);
    }

    @Test
    public void shouldParseMakaronyPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.MAKARONY.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Makarony", makaronyMenuPositions);
    }

    @Test
    public void shouldParseDrobirybyPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.DROBIRYBY.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Drób i Ryby", drobirybyMenuPositions);
    }

    // @Test TODO:
    public void shouldParseNapojePage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.NAPOJE.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Napoje zimne", napojeMenuPositions);
    }

    // @Test TODO:
    public void shouldParseDodatkiPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.DODATKI.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Dodatki", dodatkiMenuPositions);
    }

    @Test
    public void shouldParsePorkPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.PORK.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Dania z Wieprzowiny", porkMenuPositions);
    }

    @Test
    public void shouldParseSalatyPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.SALATY.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Sałatki", salatyMenuPositions);
    }
    
    @Test
    public void shouldParsePizzaPage() throws MenuProviderException {

        // given
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider();
        MenuConfiguration menuConfiguration = createMenuConfiguration(MenuPageMetadata.PIZZA.getPageName());

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(menuConfiguration);

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Pizza", pizzaMenuPositions);
    }

    private MenuPosition[] calzoneMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Calzone Classico", new BigDecimal("15.00"),
                    "pieróg z ciasta do pizzy z sosem pomidorowym, szynką, mozzarellą i pieczarkami."),
            new MenuPosition(2, "Calzone Mexicana", new BigDecimal("17.00"),
                    "pikantny pieróg z ciasta do pizzy z salami pepperoni, fasolką czerwoną, kukurydzą, sosem chilli, papryczką i serem mozzarella."),
            new MenuPosition(3, "Calzone Italiano", new BigDecimal("16.00"),
                    "pieróg z ciasta do pizzy z sosem pomidorowym, białą mozzarellą, czosnkiem, świeżą bazylią, pomidorami i oregano."),
            new MenuPosition(4, "Calzone Quattro Formaggi", new BigDecimal("17.00"),
                    "pieróg z ciasta do pizzy prosto z pieca pełen pysznych serów:mozzarella, parmezan, blue pleśniowy, feta"),
            new MenuPosition(5, "Calzone Juhas", new BigDecimal("18.00"),
                    "pieróg z ciasta do pizzy z sosem śmietanowo-czosnkowym, salami, serem wędzonym, cebulą i mozzarellą") };

    private MenuPosition[] dlaDzieciMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Danie Super Bohatera", new BigDecimal("14.00"),
                    "Filet z kurczaka, frytki – literki, marchewka + soczek z rurką gratis."),
            new MenuPosition(2, "Pizza Mini Mini", new BigDecimal("13.00"),
                    "sos pomidorowy, mozzarella, szynka + soczek z rurką gratis."),
            new MenuPosition(3, "Pizza Bambini", new BigDecimal("13.00"),
                    "sos pomidorowy, kurczaczek, kukurydza, mozzarella + soczek z rurką gratis"),
            new MenuPosition(4, "Spaghetti Neapolitana", new BigDecimal("12.00"),
                    "makaron w sosie z świeżych pomidorów z ziołami i parmezanem") };

    private MenuPosition[] makaronyMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Penne Alfredo", new BigDecimal("17.00"),
                    "Makaron z grillowanymi pieczarkami i kawałkami kurczaka w sosie śmietanowym."),
            new MenuPosition(2, "Penne Spinaci", new BigDecimal("18.00"),
                    "Makaron ze świeżym szpinakiem i boczkiem w sosie z sera pleśniowego."),
            new MenuPosition(4, "Spaghetti Bolognese", new BigDecimal("17.00"),
                    "Makaron ze świeżą wołowiną, pomidorami, ziołami i parmezan."),
            new MenuPosition(5, "Tagiatelle z łososiem", new BigDecimal("19.00"),
                    "Makaron z świeżym łososiem, rozmarynem w kremowym sosie."),
            new MenuPosition(6, "Spaghetti Carbonara", new BigDecimal("17.00"),
                    "Makaron w sosie śmietanowym z boczkiem i parmezanem."),
            new MenuPosition(7, "Spaghetti z krewetkami", new BigDecimal("22.00"),
                    "Makaron z krewetkami w białym winie, natką pietruszki i pomidorami koktajlowymi"),
            new MenuPosition(8, "Tagiatelle Peperone", new BigDecimal("19.00"),
                    "Makaron z suszonymi pomidorami, czosnkiem, papryką chilli i świeżym szpinakiem"),
            new MenuPosition(9, "Tagliatelle z polędwiczką", new BigDecimal("21.00"),
                    "Makaron z polędwiczką wieprzową, podgrzybkami, pietruszką i rozmarynem") };

    private MenuPosition[] drobirybyMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Grillowana pierś z kurczaka", new BigDecimal("17.00"),
                    "Grillowana pierś z kurczaka z serem wędzonym i żurawiną, frytki, surówki."),
            new MenuPosition(2, "Kurczak ze szpinakiem", new BigDecimal("21.00"),
                    "Filet z kurczaka nadziewany szpinakiem i czosnkiem z sosem z sera gorgonzola, frytki, surówki."),
            new MenuPosition(3, "Pierś z kurczaka z szynką parmeńską i szparagami", new BigDecimal("22.00"),
                    "Faszerowany szparagami filet z kurczaka owiniety szynką parmeńską, frytki, surówki."),
            new MenuPosition(4, "Filet z kurczaka w sosie z grzybów leśnych", new BigDecimal("18.00"),
                    "Grillowany filet z kurczaka z sosem borowikowym, frytki, surówki."),
            new MenuPosition(5, "Złocisty filet z kurczaka", new BigDecimal("17.00"),
                    "Panierowany filet z kurczaka. frytki, surówki"),
            new MenuPosition(6, "Pstrąg z świeżymi ziołami", new BigDecimal("26.00"),
                    "Pstrąg na masełku z ziołami, frytki, surówki."),
            new MenuPosition(7, "Łosoś z grilla z sosem z rukoli", new BigDecimal("28.00"),
                    "Grillowany duży stek z łososia z bialym winem, sosem z rukoli, frytki, surówki."),
            new MenuPosition(8, "Dorsz panierowany", new BigDecimal("20.00"),
                    "Panierowany filet z dorsza bałtyckiego z sosem czosnkowym, frytki, surowki") };

    private MenuPosition[] napojeMenuPositions = new MenuPosition[] {
    // TODO
    new MenuPosition(8, "Dorsz panierowany", new BigDecimal("20.00"),
            "Panierowany filet z dorsza bałtyckiego z sosem czosnkowym, frytki, surowki") };

    private MenuPosition[] dodatkiMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Frytki", new BigDecimal("5.00"), null),
            new MenuPosition(2, "Frytki - literki", new BigDecimal("7.00"), null),
            new MenuPosition(3, "Paluchy z ciasta pizzowego prosto z pieca", new BigDecimal("4.00"), "(2 szt.)"),
            new MenuPosition(4, "Sosy (czosnkowy, salsa, barbecue)", new BigDecimal("2.00"), "(1 szt.)") };

    private MenuPosition[] porkMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Schab z lasu", new BigDecimal("19.00"),
                    "Plastry schabu z sosem borowikowym, frytki, surówki."),
            new MenuPosition(2, "Chrupiący schabowy", new BigDecimal("18.00"),
                    "Kotlet schabowy panierowany, frytki, surówki."),
            new MenuPosition(3, "Mix Grill", new BigDecimal("26.00"),
                    "Dwa rodzaje mięsapodane z grillowanymi pieczarkami, frytki, surowki"),
            new MenuPosition(4, "Stek po chłopsku", new BigDecimal("19.00"),
                    "Schab grillowany z boczkiem i cebulką, frytki, surówki.") };

    private MenuPosition[] salatyMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Grecka", new BigDecimal("16.00"),
                    "Mixsałat,pomidor,ogórek,czerwonacebula,oliwki,serfeta,oliwa,sosvinegret"),
            new MenuPosition(2, "Włoska", new BigDecimal("19.00"),
                    "Mixsałat,seler naciowy, orzechy włoskie,ananas, kurczak grillowany, sos majonezowy"),
            new MenuPosition(3, "Fresca", new BigDecimal("18.00"),
                    "Mixsałat,szpinak,grapefruit,ser kozi, prażone migdały,sosz granatu"),
            new MenuPosition(4, "Salmon", new BigDecimal("20.00"),
                    "Mixsałat, oliwki, wędzony łosoś, ser lazur, pomidorki koktajlowe, sos czosnkowy"),
            new MenuPosition(5, "Cezar", new BigDecimal("18.00"),
                    "Mixsałat,pomidor,ogórek,grillowanyboczekikurczak,parmezan,sosrosemary"), };
    
    private MenuPosition[] menudniaMenuPositions = new MenuPosition[] {
            new MenuPosition(0, "Chłodnik z cukinii", new BigDecimal("3.00"), null),
            new MenuPosition(1, "Pierś drobiowa z szpinakiem zawijana szynką włoską, surówki", new BigDecimal("13.99"), null),
            new MenuPosition(2, "Tagliatelle z kurczakiem, brokułami, czosnkiem w sosie serowym", new BigDecimal("13.99"), null),
            new MenuPosition(3, "Paski schabu z sosem tzatziki, surówki", new BigDecimal("13.99"), null),
            new MenuPosition(4, "Grillowany filet z kurczaka z białą mozzarellą i sosem pesto , surówki", new BigDecimal("13.99"), null),
            new MenuPosition(5, "Pizza ”Siena” 30cm: sos pomidorowy, szynka, kukurydza, pomidor, cebula, mozzarella", new BigDecimal("13.99"), null),
            new MenuPosition(6, "Pizza ”Baca” 30cm: sos pomidorowy, pieczarki, boczek, ser wędzony, ogórek, mozzarella", new BigDecimal("13.99"), null),
            new MenuPosition(7, "Sałatka „Szefa”: mix sałat, pomidor, ogórek, grillowany kurczak, Camembert, winogrona, sos", new BigDecimal("13.99"), null) };

    private MenuPosition[] pizzaMenuPositions = new MenuPosition[] {
            new MenuPosition(1, "Grecka", new BigDecimal("16.00"),
                    "Mixsałat,pomidor,ogórek,czerwonacebula,oliwki,serfeta,oliwa,sosvinegret"),
            new MenuPosition(5, "Cezar", new BigDecimal("18.00"),
                    "Mixsałat,pomidor,ogórek,grillowanyboczekikurczak,parmezan,sosrosemary"), };
    
    private void ensureMenuPositionsAreCorrect(List<MenuPage> menuPages, String expectedCategoryName,
            MenuPosition[] expectedMenuPositions) {
        assertNotNull(menuPages);
        assertEquals(menuPages.size(), 1);
        MenuPage page = menuPages.get(0);

        assertEquals(page.getCategory(), expectedCategoryName);
        assertEquals(page.getMenuPositions().size(), expectedMenuPositions.length);
        assertEquals(page.getMenuPositions().toArray(), expectedMenuPositions);
    }

}