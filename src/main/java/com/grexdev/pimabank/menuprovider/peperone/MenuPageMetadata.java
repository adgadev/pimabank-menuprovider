package com.grexdev.pimabank.menuprovider.peperone;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MenuPageMetadata {
    MENUDNIA("menudnia", "", "peperone/menudnia.regex", "peperone/menudnia.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    CALZONE("calzone", "calzone-menu", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    DLADZIECI("dladzieci", "dladzieci-menu-all", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    DROBIRYBY("drobiryby", "drobiryby-menu-all", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    MAKARONY("makarony", "makarony-menu-all", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    PORK("pork", "pork-menu", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    SALATY("salaty", "salaty-menu-all", "peperone/generic.regex", "peperone/generic.vm", false, //
            "//table[@class='contentpaneopen']//span//node()"), //
    PIZZA("pizza", "pizza", "peperone/pizza.regex", "peperone/pizza.vm", false, //
            "//table[@class='contentpaneopen']//p//node()"), //
    // TODO: fix
    NAPOJE("napoje", "napoje-menu-all", "peperone/generic.regex", "peperone/generic.vm", true, //
            "//table[@class='contentpaneopen']//span//node()"), //
    // TODO: fix
    DODATKI("dodatki", "dodatki-all", "peperone/dodatki.regex", "peperone/generic.vm", true, //
            "//table[@class='contentpaneopen']//span//node()"), //
    ;

    private final String pageName;

    private final String urlSuffix;

    private final String parserRegexpResource;

    private final String velocityTemplateResource;
    
    private final boolean pageDisabled;

    private final String xPathExpression;

    private MenuPageMetadata(String pageName, String urlSuffix, String parserRegexpResource, String velocityTemplateResource,
            boolean pageDisabled, String xPathExpression) {
        this.pageName = pageName;
        this.urlSuffix = urlSuffix;
        this.parserRegexpResource = parserRegexpResource;
        this.velocityTemplateResource = velocityTemplateResource;
        this.xPathExpression = xPathExpression;
        this.pageDisabled = pageDisabled;
    }
}