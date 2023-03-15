package models;

import lombok.Getter;
import lombok.Setter;

public class ProductInfo {
    @Getter @Setter private String image;
    @Getter @Setter private String productLink;
    @Getter @Setter private String title;
    @Getter @Setter private double unitPrice;
    @Getter @Setter private double total;
    @Getter @Setter private int quantity;
    @Getter @Setter private String model;
    @Getter @Setter private String productName;

    @Getter @Setter private char unitPriceCurrency;
    @Getter @Setter private char totalCurrency;
}