package com.mousebelly.app.userapp.mealPlanner;

import org.json.JSONObject;

/**
 * Created by Aaru on 07/04/2017.
 */

public class FoodObjectInOrderPlan {
    private String PID;
    private String Product_Name;
    private String status;
    private String HWID;
    private String Qty;
    private String stars;
    private String Price_item;
    private String orderid;
    private String delivery_date;
    private String Image;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHWID() {
        return HWID;
    }

    public void setHWID(String HWID) {
        this.HWID = HWID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPrice_item() {
        return Price_item;
    }

    public void setPrice_item(String price_item) {
        Price_item = price_item;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "FoodObjectInOrderPlan{" +
                "PID='" + PID + '\'' +
                ", Product_Name='" + Product_Name + '\'' +
                ", status='" + status + '\'' +
                ", HWID='" + HWID + '\'' +
                ", Qty='" + Qty + '\'' +
                ", stars='" + stars + '\'' +
                ", Price_item='" + Price_item + '\'' +
                ", orderid='" + orderid + '\'' +
                ", delivery_date='" + delivery_date + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("PID", PID);
            jsonObject.put("Product_Name", Product_Name);
            jsonObject.put("status", status);
            jsonObject.put("HWID", HWID);
            jsonObject.put("PID", PID);
            jsonObject.put("Qty", Qty);
            jsonObject.put("stars", stars);
            jsonObject.put("Price_item", Price_item);
            jsonObject.put("orderid", orderid);
            jsonObject.put("delivery_date", delivery_date);

            System.out.println("Image : " + Image);

            jsonObject.put("Image", Image);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
