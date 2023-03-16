package models;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Product {
    private String title;
    private String model;
    private String image;
    private int id;
    private String inStock;
    private String brand;
    private int rewardPoints;
    private double unitPrice;
}