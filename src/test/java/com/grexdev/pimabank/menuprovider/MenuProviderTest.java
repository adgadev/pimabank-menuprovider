package com.grexdev.pimabank.menuprovider;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;

import com.grexdev.pimabank.menuprovider.MenuProviderFactory.Restaurant;
import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.dto.MenuPosition;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;

/**
 * Integration (pom.xml) : 
 * 
 *   <dedependencies>
 *       <dependency>
 *           <groupId>com.grexdev.pimabank</groupId>
 *           <artifactId>restaurants-menu-provider</artifactId>
 *           <version>0.2.2</version>
 *       </dependency>
 *   </dependencies>
 *   
 *   <repositories>
 *       <repository>
 *          <id>github-grexag-repository</id>
 *           <url>https://raw.github.com/grexag/pimabank-menuprovider/mvn-repo/</url>
 *           <releases>
 *               <enabled>true</enabled>
 *           </releases>
 *           <snapshots>
 *               <enabled>false</enabled>
 *           </snapshots>
 *       </repository>
 *   </repositories>
 */
@Slf4j
public class MenuProviderTest {

    @Test(groups = "endToEnd")
    public void shouldGetPeperoneMenu() throws MenuProviderException {
        // given
        MenuProviderFactoryConfiguration configuration = MenuProviderFactoryConfiguration.getDefaultConfiguration();
        MenuProviderFactory factory = new MenuProviderFactory(configuration);
        MenuProvider peperoneMenuProvider = factory.createMenuProvider(Restaurant.PEPERONE);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        printMenu("PEPERONE",menuPages);
    }
    
    @Test(groups = "endToEnd")
    public void shouldGetPhoungDongMenu() throws MenuProviderException {

        // given
        MenuProviderFactoryConfiguration configuration = MenuProviderFactoryConfiguration.getDefaultConfiguration();
        MenuProviderFactory factory = new MenuProviderFactory(configuration);
        MenuProvider peperoneMenuProvider = factory.createMenuProvider(Restaurant.PHUONG_DONG);

        // when
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu();

        // then
        printMenu("PHUONG DONG", menuPages);
    }
   
    private void printMenu(String name, List<MenuPage> menuPages) {
        log.info("========================= {} MENU =======================\n", name);

        for (MenuPage menuPage : menuPages) {
            log.info("############# Category = {} ##########", menuPage.getCategory());

            for (MenuPosition menuPosition : menuPage.getMenuPositions()) {
                log.info(menuPosition.toString());
            }
        }
    }

}
