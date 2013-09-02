package com.grexdev.pimabank.menuprovider.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import javax.xml.xpath.XPathExpressionException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.xml.sax.SAXException;

import com.grexdev.pimabank.menuprovider.MenuProvider;
import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.MenuPageDescriptor;
import com.grexdev.pimabank.menuprovider.parser.descriptor.RestaurantDescriptor;
import com.grexdev.pimabank.menuprovider.parser.regex.NamedGroupConfigurationProvider;
import com.grexdev.pimabank.menuprovider.parser.regex.NamedGroupRegistry;
import com.grexdev.pimabank.menuprovider.parser.regex.RegexGroupExtractor;
import com.grexdev.pimabank.menuprovider.parser.utils.DigesterDtoInstantiator;
import com.grexdev.pimabank.menuprovider.parser.utils.HtmlPageTextContentExtractor;
import com.grexdev.pimabank.menuprovider.parser.utils.HttpWebPageFetcher;
import com.grexdev.pimabank.menuprovider.parser.utils.VelocityXmlTemplator;

@Slf4j
public class RegexParserMenuProvider implements MenuProvider {
    
    private final RegexParserMenuProviderConfiguration configuration;
    
    private final RestaurantDescriptor restaurantDescriptor;

    private final HttpWebPageFetcher httpPageDownloader;

    private NamedGroupConfigurationProvider regexpConfigurationProvider = new NamedGroupConfigurationProvider();

    private VelocityXmlTemplator templator = new VelocityXmlTemplator();

    private DigesterDtoInstantiator dtoInstantiator = new DigesterDtoInstantiator();

    public RegexParserMenuProvider(RegexParserMenuProviderConfiguration configuration, RestaurantDescriptor restaurantDescriptor) {
        this.configuration = configuration;
        this.restaurantDescriptor = restaurantDescriptor;
        httpPageDownloader =  new HttpWebPageFetcher(configuration);
    }
    
    @Override
    public List<MenuPage> fetchRestaurantMenu() throws MenuProviderException {
        List<MenuPage> menuPages = new ArrayList<MenuPage>();
        ExecutorService extractorThreadPool = Executors.newSingleThreadScheduledExecutor();
        
        try {
            for (MenuPageDescriptor menuPageDescriptor : restaurantDescriptor.getMenuPages()) {
                if (menuPageDescriptor.isPageDisabled() == false) {
                    log.info("Fetching / parsing menu for page {}", menuPageDescriptor);
                    menuPages.add(fetchRestaurantMenuPage(restaurantDescriptor.getBaseUrl(), menuPageDescriptor, extractorThreadPool));
                }
            }
        } finally {
            extractorThreadPool.shutdown();
        }

        return menuPages;
    }
    
    private MenuPage fetchRestaurantMenuPage(String restaurantPageBaseUrl, MenuPageDescriptor menuPageDescriptor, ExecutorService extractorThreadPool) throws MenuProviderException {
        try {
            String fullPageUrl = restaurantPageBaseUrl + menuPageDescriptor.getUrlSuffix();
            InputStream inputStream = httpPageDownloader.downloadPage(fullPageUrl);

            HtmlPageTextContentExtractor htmlToTextTransformer = new HtmlPageTextContentExtractor();
            String text = htmlToTextTransformer.extractTextContent(menuPageDescriptor, inputStream);
            
            Map<String, String> namedRegexpGroups = regexpConfigurationProvider.getNamedRegexpGroups(
                    configuration.getCommonsRegexResourceName(), menuPageDescriptor.getParserRegexpResource());
            RegexGroupExtractor regexGroupExtractor = new RegexGroupExtractor(configuration, 
                    new NamedGroupRegistry(namedRegexpGroups), extractorThreadPool);
            Object rootGroup = regexGroupExtractor.extractRegexGroupsValues(text);

            String document = templator.createDocumentUsingTemplate(menuPageDescriptor, rootGroup);
            return dtoInstantiator.convertXmlToMenuListDto(document);

        } catch (IOException | XPathExpressionException | SAXException | ConfigurationException | InterruptedException | ExecutionException e) {
            throw new MenuProviderException(
                    "Wrong configuration or downloading / fetching / parsing failed / page structure has changed", e);
        } catch (RuntimeException e) {
            throw new MenuProviderException("Unexprected runtime exception during fetching/parsing restaurant menu", e);    
            
        } catch (TimeoutException e) {
           throw new MenuProviderException("Timeout during matching regexp expression (Regexp definitions in " 
                   + menuPageDescriptor.getParserRegexpResource() + " are invalid or page content has changed recently", e);
        }
    }

}
