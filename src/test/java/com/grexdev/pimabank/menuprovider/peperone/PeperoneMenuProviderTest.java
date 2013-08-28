package com.grexdev.pimabank.menuprovider.peperone;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.MenuPageDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptorProvider;
import com.grexdev.pimabank.menuprovider.peperone.PeperoneMenuProvider;

public class PeperoneMenuProviderTest {

    private static final int SERVER_PORT = 18723;

    private HttpPageFromClasspathServer server = new HttpPageFromClasspathServer("peperone/peperone.pl-thursday");

    private MenuProviderConfiguration configuration = new MenuProviderConfiguration();
    
    @BeforeClass
    public void setUp() throws IOException {
        server.startServer(SERVER_PORT);
    }

    @AfterClass
    public void tearDown() throws IOException {
        server.stopServer();
    }
    
    private RestaurantDescriptor createRestaurantDescriptor(String menuPage) throws MenuProviderException {
        RestaurantDescriptor restaurantDescriptor = new RestaurantDescriptorProvider().getRestaurantDescriptor("peperone-descriptor.xml");
        restaurantDescriptor.setBaseUrl(String.format("http://localhost:%s/", SERVER_PORT)); // without it uses real page
        
        // alter descriptor to use only one page, which matches given name
        for (MenuPageDescriptor menuPageDescriptor : restaurantDescriptor.getMenuPages()) {
            if (menuPageDescriptor.getPageName().equals(menuPage)) {
                restaurantDescriptor.setMenuPages(Arrays.asList(menuPageDescriptor));
                return restaurantDescriptor;
            }
        }
        throw new RuntimeException("Page with given name not found in descriptor");
    }

    @Test
    public void shouldParseMenudniaPage() throws MenuProviderException {
        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("menudnia");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Danie dnia", menudniaMenuPositions);
    }

    @Test
    public void shouldParseCalzonePage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("calzone");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Calzone", calzoneMenuPositions);
    }

    @Test
    public void shouldParseDlaDzieciPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("dladzieci");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Menu dla dzieci", dlaDzieciMenuPositions);
    }

    @Test
    public void shouldParseMakaronyPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("makarony");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Makarony", makaronyMenuPositions);
    }

    @Test
    public void shouldParseDrobirybyPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("drobiryby");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Drób i Ryby", drobirybyMenuPositions);
    }

    // @Test TODO:
    public void shouldParseNapojePage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("napoje");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Napoje zimne", napojeMenuPositions);
    }

    // @Test TODO:
    public void shouldParseDodatkiPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("dodatki");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Dodatki", dodatkiMenuPositions);
    }

    @Test
    public void shouldParsePorkPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("pork");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Dania z Wieprzowiny", porkMenuPositions);
    }

    @Test
    public void shouldParseSalatyPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("salaty");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        ensureMenuPositionsAreCorrect(menuPages, "Sałatki", salatyMenuPositions);
    }

    @Test
    public void shouldParsePizzaPage() throws MenuProviderException {

        // given
        RestaurantDescriptor restaurantDescriptor = createRestaurantDescriptor("pizza");
        PeperoneMenuProvider peperoneMenuProvider = new PeperoneMenuProvider(configuration, restaurantDescriptor);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

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
            new MenuPosition(1, "Schab z lasu", new BigDecimal("19.00"), "Plastry schabu z sosem borowikowym, frytki, surówki."),
            new MenuPosition(2, "Chrupiący schabowy", new BigDecimal("18.00"), "Kotlet schabowy panierowany, frytki, surówki."),
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
            new MenuPosition(0, "Chłodnik z cukinii", "Zupa", new BigDecimal("3.00"), null),
            new MenuPosition(1, "Pierś drobiowa z szpinakiem zawijana szynką włoską, surówki", "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(2, "Tagliatelle z kurczakiem, brokułami, czosnkiem w sosie serowym", "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(3, "Paski schabu z sosem tzatziki, surówki", "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(4, "Grillowany filet z kurczaka z białą mozzarellą i sosem pesto , surówki",
                    "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(5, "Pizza ”Siena” 30cm: sos pomidorowy, szynka, kukurydza, pomidor, cebula, mozzarella",
                    "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(6, "Pizza ”Baca” 30cm: sos pomidorowy, pieczarki, boczek, ser wędzony, ogórek, mozzarella",
                    "Danie", new BigDecimal("13.99"), null),
            new MenuPosition(7, "Sałatka „Szefa”: mix sałat, pomidor, ogórek, grillowany kurczak, Camembert, winogrona, sos",
                    "Danie", new BigDecimal("13.99"), null) };

    private MenuPosition[] pizzaMenuPositions = new PizzaListBuilder() //
            .addPizza(1, "Margarita", "14.00", "21.00", "30.00", "38.00", "Sos pomidorowy, ser, oregano") //
            .addPizza(2, "Funghi", "15.00", "22.00", "31.00", "39.00", "Sos pomidorowy, ser, pieczarki, oregano") //
            .addPizza(3, "Vesuvio", "16.00", "23.00", "32.00", "40.00", "Sos pomidorowy, ser, pieczarki, szynka, oregano") //
            .addPizza(4, "Tuna", "16.00", "23.00", "32.00", "40.00", "Sos pomidorowy, ser, cebula, tuńczyk, oregano") //
            .addPizza(5, "Salami", "17.00", "23.00", "32.00", "42.00", "Sos pomidorowy, ser, pieczarki, salami, oregano") //
            .addPizza(6, "Bianco", "17.00", "24.00", "33.00", "42.00",
                    "Sos śmietanowo-czosnkowy, ser, szynka, brokuły, kukurydza,  oregano") //
            .addPizza(7, "Toscana", "17.00", "25.00", "34.00", "42.00", "Sos pomidorowy, ser, salami, rukola, pomidor, oregano") //
            .addPizza(8, "Wegetariańska", "17.00", "25.00", "34.00", "43.00",
                    "Sos pomidorowy, ser, pieczarki,kukurydza, cebula, brokuły, papryka, oliwki, pomidor, oregano") //
            .addPizza(9, "Capricciossa", "17.00", "25.00", "34.00", "44.00",
                    "Sos pomidorowy, ser, pieczarki, szynka, kukurydza, papryka, oregano") //
            .addPizza(10, "Hawajska", "17.00", "25.00", "34.00", "44.00", "Sos pomidorowy, ser,szynka, ananas, oregano") //
            .addPizza(11, "Amore", "17.00", "25.00", "34.00", "44.00",
                    "Sos pomidorowy, ser, pieczarki, kurczak, szynka, oliwki, oregano") //
            .addPizza(12, "Sycylijska", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, salami, tuńczyk, kukurydza, pomidor, oliwki, oregano") //
            .addPizza(13, "Królewska", "20.00", "28.00", "37.00", "45.00",
                    "Sos śmietanowo-czosnkowy, ser, parmezan, marynowane kawałki kurczaka, szynka, oliwki, pomidor") //
            .addPizza(14, "Pepperoni", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, salami pepperoni, papryka pepperoni, oregano") //
            .addPizza(15, "Frutti di Mare", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, pieczarki, owoce morza, papryka, oregano") //
            .addPizza(16, "Los Qurczakos", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, kurczak, szynka, kukurydza, oregano") //
            .addPizza(17, "Marysieńka", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, szynka, salami, papryka, cebula, oregano") //
            .addPizza(18, "Rimini", "19.00", "26.00", "35.00", "43.00",
                    "Sos pomidorowy, ser, pieczarki, szynka, jajko, salami, boczek, cebula, oregano") //
            .addPizza(19, "Diabelska", "19.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, szynka, salami, boczek, cebula,papryka jalapeno, tabasco, oregano") //
            .addPizza(20, "Karola", "19.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, szynka, kurczak, ananas, oregano") //
            .addPizza(21, "Peperone", "20.00", "27.00", "36.00", "45.00",
                    "Sos pomidorowy, mozzarella, salami peppreoni, oliwki, papryka pepperoni, pikantny filet z kurczaka") //
            .addPizza(22, "Quattro Formaggi", "20.00", "27.00", "36.00", "45.00",
                    "Sos pomidorowy, 4 rodzaje sera: mozzarella, feta, ser wędzony, parmezan, oregano") //
            .addPizza(23, "Swojska", "20.00", "27.00", "36.00", "46.00",
                    "Sos pomidorowy, podwójny ser, pieczarki, szynka, kiełbasa, salami, boczek, ogórek, oregano") //
            .addPizza(24, "India", "19.00", "27.00", "36.00", "46.00",
                    "Sos curry, ser, szynka, kukurydza, kurczak, brokuły, ogórek, cebula, oregano") //
            .addPizza(25, "Kamikadze", "21.00", "28.00", "37.00", "47.00",
                    "Sos pomidorowy, pieczarki, kurczak, bekon, szynka, sos chilli, papryka pepperoni, oregano") //
            .addPizza(26, "Pizza Maxx", "21.00", "28.00", "37.00", "47.00",
                    "Sos pomidorowy, podwójny ser, pieczarki, szynka, kiełbasa, salami, boczek, kukurydza, papryka, oregano") //
            .addPizza(27, "Kompozycja własna", "22.00", "30.00", "39.00", "49.00", "8 dowolnych skłądników") //
            .addPizza(28, "Italiana", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser parmezan, mozarella, salami, boczek, cebula, pomidor, czosnek, oregano") //
            .addPizza(29, "Dragon", "21.00", "28.00", "37.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, szynka,salami, oliwki, kurcak, cebula, papryka jalapeno, sos chilli, oregano") //
            .addPizza(30, "Zemsta kucharza", "19.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, salami, boczek, pepperoni, cebula, tabasco") //
            .addPizza(31, "Gangsterska", "18.00", "26.00", "35.00", "45.00",
                    "Sos pomidorowy, ser, pieczarki, kiełbasa, bekon, ogórek, papryka, cebula, czosnek, oregano") //
            .addPizza(32, "Kebab Pizza", "21.00", "28.00", "37.00", "46.00",
                    "Sos pomidorowy, ser, kawałki kurczaka, cebula, ogórek świeży, pomidor, kapusta pekińska") //
            .addPizza(33, "Parma", "23.00", "30.00", "39.00", "49.00",
                    "Sos pomidorowy, cieńkie ciasto, szynka parmeńska, rukola, parmezan, oregano") //
            .addPizza(34, "Specjale", "21.00", "29.00", "38.00", "48.00",
                    "Sos pomidorowy, mozzarella, lazur pleśniowy, marynowane kawałki kurczaka, szynka, szpinak, rukola i czosnek") //
            .toMenuPositionArray();

    private void ensureMenuPositionsAreCorrect(List<MenuPage> menuPages, String expectedCategoryName,
            MenuPosition[] expectedMenuPositions) {
        assertNotNull(menuPages);
        assertEquals(menuPages.size(), 1);
        MenuPage page = menuPages.get(0);

        assertEquals(page.getCategory(), expectedCategoryName);
        assertEquals(page.getMenuPositions().size(), expectedMenuPositions.length);
        assertEquals(page.getMenuPositions().toArray(), expectedMenuPositions);
    }

    private class PizzaListBuilder {

        private List<MenuPosition> list = new ArrayList<MenuPosition>();

        private PizzaListBuilder addPizza(int id, String name, String price30, String price40, String price50, String price60,
                String desc) {
            list.add(new MenuPosition(id, name, "Ø30", new BigDecimal(price30), desc));
            list.add(new MenuPosition(id, name, "Ø40", new BigDecimal(price40), desc));
            list.add(new MenuPosition(id, name, "Ø50", new BigDecimal(price50), desc));
            list.add(new MenuPosition(id, name, "Ø60", new BigDecimal(price60), desc));
            return this;
        }

        private MenuPosition[] toMenuPositionArray() {
            return list.toArray(new MenuPosition[0]);
        }
    }
}