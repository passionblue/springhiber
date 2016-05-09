package com.datagen.test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Order {

    private static Logger m_logger = LoggerFactory.getLogger(Order.class);

    private String  sku;
    private Integer qty;
    private Double  price;
    private String  total;
    private String  shipDate;
    private Boolean shipped;
    
    //Default Constructor
    public Order() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public Boolean getShipped() {
        return shipped;
    }

    public void setShipped(Boolean shipped) {
        this.shipped = shipped;
    }

    @Override
    public String toString() {
        return "Order [sku=" + sku + ", qty=" + qty + ", price=" + price + ", total=" + total + ", shipDate=" + shipDate + ", shipped=" + shipped + "]";
    }

    
    
    
    
}
