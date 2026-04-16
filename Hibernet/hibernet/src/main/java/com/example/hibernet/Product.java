package com.example.hibernet;

//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ProductDate")

public class Product {
    @Id
    
    int productId;
   // @Column
    String productname;
    double price;
    public Product(int productId, String productname, double price) {
        this.productId = productId;
        this.productname = productname;
        this.price = price;
    }
    public Product() {
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductname() {
        return productname;
    }
    public void setProductname(String productname) {
        this.productname = productname;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    


}
