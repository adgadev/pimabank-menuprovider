package com.grexdev.pimabank.menuprovider.parser.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.grexdev.pimabank.menuprovider.parser.descriptor.MenuPageDescriptor;

@Slf4j
public class HtmlPageTextContentExtractor {

    private XPathFactory xPathFactory = XPathFactory.newInstance();

    public String extractTextContent(MenuPageDescriptor pageDescriptor, InputStream inputStream) throws XPathExpressionException, MenuProviderException {
        Document document = getValidXmlDocument(inputStream);
        XPathExpression xPathExpression = constructXPathExpression(pageDescriptor.getXPathExpression());
        Object result = xPathExpression.evaluate(document, XPathConstants.NODESET);

        StringBuilder textContent = new StringBuilder();
        log.debug("Extracting values of xpath expressions");
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            // TODO: check if replace needed
            String content = node.getNodeValue().replace("\u00a0","");
            String trimmedContent = StringUtils.trimToNull(content);
            
            if (trimmedContent != null) {
              //  log.debug(trimmedContent);
                textContent.append(trimmedContent);
                textContent.append('\n');
            }
        }
        
        log.debug("Extracted text is >>>{}<<<", textContent.toString());
        
        if (StringUtils.isEmpty(textContent)) {
            throw new MenuProviderException("Data extracted from page content using XPath expression is empty, page=" + pageDescriptor.getPageId());
        }
        
        return textContent.toString();
    }

    private Document getValidXmlDocument(InputStream inputStream) {
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
                
        Reader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        Document tidyDOM = tidy.parseDOM(inputStreamReader, null);
        return tidyDOM;
    }

    private XPathExpression constructXPathExpression(String expression) throws XPathExpressionException {
        XPath xPath = xPathFactory.newXPath();
        return xPath.compile(expression);
    }

}
