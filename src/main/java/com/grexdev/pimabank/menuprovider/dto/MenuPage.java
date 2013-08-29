package com.grexdev.pimabank.menuprovider.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetNext;

@Getter
@ToString
@ObjectCreate(pattern = "menu")
public class MenuPage implements Serializable {

    private static final long serialVersionUID = -4849805686338998410L;

    @Setter
    @BeanPropertySetter(pattern = "menu/category")
    private String category;

    private final List<MenuPosition> menuPositions = new ArrayList<MenuPosition>();

    @SetNext
    public void addItem(MenuPosition menuPosition) {
        this.menuPositions.add(menuPosition);
    }

}
