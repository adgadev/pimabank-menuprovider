package com.grexdev.pimabank.menuprovider.parser.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.xml.sax.SAXException;

import com.grexdev.pimabank.menuprovider.dto.MenuPage;

public class DigesterDtoInstantiator {

    private Digester digester;

    public DigesterDtoInstantiator() {
        DigesterLoader digesterLoader = DigesterLoader.newLoader(new FromAnnotationsRuleModule() {
            @Override
            protected void configureRules() {
                bindRulesFrom(MenuPage.class); //
            }
        });

        digester = digesterLoader.newDigester();

        Converter converter = new Converter() {
            
            private BigDecimal ONE_HUNDRED = new BigDecimal("100");
            
            @Override
            public Object convert(Class clazz, Object value) {
                String numberStr = value.toString().replace(',','.');
                BigDecimal number = new BigDecimal(numberStr);
                BigDecimal normalizedNumber = new BigDecimal(number.multiply(ONE_HUNDRED).toBigIntegerExact(), 2);
                return normalizedNumber;
            }};
        ConvertUtils.register(converter, BigDecimal.class);
    }

    public MenuPage convertXmlToMenuListDto(String document) throws IOException, SAXException {
        try (Reader reader = new StringReader(document)) {
            return digester.parse(reader);
        }
    }
}
