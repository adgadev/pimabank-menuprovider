package com.grexdev.pimabank.menuprovider.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@ObjectCreate(pattern = "menu/menupositions/menuposition")
public class MenuPosition {

    @BeanPropertySetter(pattern = "menu/menupositions/menuposition/id")
    private int id;

    @BeanPropertySetter(pattern = "menu/menupositions/menuposition/name")
    private String name;
    
    @BeanPropertySetter(pattern = "menu/menupositions/menuposition/subtype")
    private String subtype;
    
    @BeanPropertySetter(pattern = "menu/menupositions/menuposition/price")
    private BigDecimal price;
    
    @BeanPropertySetter(pattern = "menu/menupositions/menuposition/description")
    private String description;
    
    public MenuPosition(int id, String name, BigDecimal price, String description) {
        this(id, name, null, price, description);
    }

}
