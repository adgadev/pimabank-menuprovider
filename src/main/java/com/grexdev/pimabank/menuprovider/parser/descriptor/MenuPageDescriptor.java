package com.grexdev.pimabank.menuprovider.parser.descriptor;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@XStreamAlias("menuPage")
public class MenuPageDescriptor {

    private String pageId;
    
    private String pageName;

    private String urlSuffix;

    private String parserRegexpResource;

    private String velocityTemplateResource;

    private boolean pageDisabled;

    private String xPathExpression;

}