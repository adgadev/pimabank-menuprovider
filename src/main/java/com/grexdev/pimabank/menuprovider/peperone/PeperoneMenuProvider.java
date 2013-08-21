package com.grexdev.pimabank.menuprovider.peperone;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.xml.sax.SAXException;

import com.grexdev.pimabank.menuprovider.MenuConfiguration;
import com.grexdev.pimabank.menuprovider.MenuProvider;
import com.grexdev.pimabank.menuprovider.dto.MenuPage;
import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.DigesterDtoInstantiator;
import com.grexdev.pimabank.menuprovider.parser.HtmlPageTextContentExtractor;
import com.grexdev.pimabank.menuprovider.parser.HttpWebPageFetcher;
import com.grexdev.pimabank.menuprovider.parser.NamedGroupRegistry;
import com.grexdev.pimabank.menuprovider.parser.RegexGroupExtractor;
import com.grexdev.pimabank.menuprovider.parser.NamedGroupConfigurationProvider;
import com.grexdev.pimabank.menuprovider.parser.VelocityXmlTemplator;

@Slf4j
public class PeperoneMenuProvider implements MenuProvider {

    private HttpWebPageFetcher httpPageDownloader = new HttpWebPageFetcher();

    private NamedGroupConfigurationProvider regexpConfigurationProvider = new NamedGroupConfigurationProvider();

    private VelocityXmlTemplator templator = new VelocityXmlTemplator();

    private DigesterDtoInstantiator dtoInstantiator = new DigesterDtoInstantiator();

    @Override
    public List<MenuPage> fetchRestaurantMenu(MenuConfiguration configuration) throws MenuProviderException {
        List<MenuPage> menuPages = new ArrayList<MenuPage>();

        for (MenuPageMetadata menuPageMetadata : MenuPageMetadata.values()) {
            if (configuration.getPageName() == null || configuration.getPageName().equals(menuPageMetadata.getPageName())) {
                log.info("Fetching / parsing menu for page {}", menuPageMetadata);
                menuPages.add(fetchRestaurantMenuPage(configuration.getRestaurantPageBaseUrl(), menuPageMetadata));
            }
        }

        return menuPages;
    }

    private MenuPage fetchRestaurantMenuPage(String restaurantPageBaseUrl, MenuPageMetadata menuPageMetadata) throws MenuProviderException {
        try {
            String fullPageUrl = restaurantPageBaseUrl + menuPageMetadata.getUrlSuffix();
            InputStream inputStream = httpPageDownloader.downloadPage(fullPageUrl);

            HtmlPageTextContentExtractor htmlToTextTransformer = new HtmlPageTextContentExtractor();
            String text = htmlToTextTransformer.extractTextContent(inputStream, menuPageMetadata.getXPathExpression());

            Map<String, String> namedRegexpGroups = regexpConfigurationProvider.getNamedRegexpGroups(menuPageMetadata.getParserRegexpResource());
            RegexGroupExtractor regexGroupExtractor = new RegexGroupExtractor(new NamedGroupRegistry(namedRegexpGroups));
            Object rootGroup = regexGroupExtractor.extractRegexGroupsValues(text);

            String document = templator.createDocumentUsingTemplate(menuPageMetadata.getVelocityTemplateResource(), rootGroup);
            return dtoInstantiator.convertXmlToMenuListDto(document);

        } catch (IOException | XPathExpressionException | SAXException | ConfigurationException e) {
            throw new MenuProviderException(
                    "Wrong configuration or downloading / fetching / parsing failed / page structure has changed", e);
        } catch (RuntimeException e) {
            throw new MenuProviderException("Unexprected runtime exception during fetching/parsing restaurant menu", e);
        }
    }

}
