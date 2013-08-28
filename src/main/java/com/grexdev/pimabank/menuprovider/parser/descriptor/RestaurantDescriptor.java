package com.grexdev.pimabank.menuprovider.parser.descriptor;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // only for purposes of altering read descriptor !
@ToString
@XStreamAlias("restaurant")
public class RestaurantDescriptor {

    private String name;

    private String baseUrl;

    private List<MenuPageDescriptor> menuPages;

}
