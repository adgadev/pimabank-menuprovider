package com.grexdev.pimabank.menuprovider.peperone;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MenuPageMetadata {
    MENUDNIA("menudnia", "", "peperone/menudnia.regex", "peperone/menudnia.vm"), //
    CALZONE("calzone", "calzone-menu", "peperone/generic.regex", "peperone/generic.vm"), //
    DLADZIECI("dladzieci", "dladzieci-menu-all", "peperone/generic.regex", "peperone/generic.vm"), //
    DROBIRYBY("drobiryby", "drobiryby-menu-all", "peperone/generic.regex", "peperone/generic.vm"), //
    MAKARONY("makarony", "makarony-menu-all", "peperone/generic.regex", "peperone/generic.vm"), //
    PORK("pork", "pork-menu", "peperone/generic.regex", "peperone/generic.vm"), //
    SALATY("salaty", "salaty-menu-all", "peperone/generic.regex", "peperone/generic.vm"), //
    // TODO:
    PIZZA("pizza", "pizza", "peperone/pizza.regex", "peperone/pizza.vm"), //
    // TODO: fix
    NAPOJE("napoje", "napoje-menu-all", "peperone/generic.regex", "peperone/generic.vm"), //
    // TODO: fix
    DODATKI("dodatki", "dodatki-all", "peperone/dodatki.regex", "peperone/generic.vm"), //
    ;

    private final String pageName;

    private final String urlSuffix;

    private final String parserRegexpResource;

    private final String velocityTemplateResource;

    private MenuPageMetadata(String pageName, String urlSuffix, String parserRegexpResource, String velocityTemplateResource) {
        this.pageName = pageName;
        this.urlSuffix = urlSuffix;
        this.parserRegexpResource = parserRegexpResource;
        this.velocityTemplateResource = velocityTemplateResource;
    }
}