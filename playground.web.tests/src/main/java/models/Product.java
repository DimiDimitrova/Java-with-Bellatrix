package models;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter @Setter private String title;
    @Getter @Setter private String model;
    @Getter @Setter private String image ;
    @Getter @Setter private int id;
    @Getter @Setter private String inStock;
    @Getter @Setter private String brand;
    @Getter @Setter private int rewardPoints;
    @Getter @Setter private double unitPrice;
}