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
 *           <version>0.2.0</version>
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

    @Test(enabled = false)
    public void shouldGetPeperoneMenu() throws MenuProviderException {

        MenuProviderFactory factory = new MenuProviderFactory();
        MenuProvider peperoneMenuProvider = factory.createMenuProvider(Restaurant.PEPERONE);

        MenuConfiguration configuration = new MenuConfiguration("http://peperone.pl/");
        List<MenuPage> menuPages = peperoneMenuProvider.fetchRestaurantMenu(configuration);

        printMenu(menuPages);
    }
    
    
    private void printMenu(List<MenuPage> menuPages) {
        log.info("========================= PEPERONE MENU =======================\n");

        for (MenuPage menuPage : menuPages) {
            log.info("############# Category = {} ##########", menuPage.getCategory());

            for (MenuPosition menuPosition : menuPage.getMenuPositions()) {
                log.info(menuPosition.toString());
            }
        }
    }

}
