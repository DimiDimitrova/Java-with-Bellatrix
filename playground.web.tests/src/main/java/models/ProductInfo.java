package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfo {
    private String image;
    private String productLink;
    private String title;
    private double unitPrice;
    private double total;
    private int quantity;
    private String model;
    private String productName;

    private char unitPriceCurrency;
    private char totalCurrency;
}