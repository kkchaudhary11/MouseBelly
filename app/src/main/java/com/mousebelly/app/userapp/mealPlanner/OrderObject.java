package com.mousebelly.app.userapp.mealPlanner;

import java.util.HashMap;

/**
 * Created by Aaru on 07/04/2017.
 */

public class OrderObject {
    private HashMap<String, FoodObjectInOrderPlan> orderItems = new HashMap<>();

    private String userEmailId;
    private String Total_bill;
    private String Payment_status;
    private String order_date;
    private String order_Id;

    private String _id;

    public OrderObject() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public HashMap<String, FoodObjectInOrderPlan> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(HashMap<String, FoodObjectInOrderPlan> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getTotal_bill() {
        return Total_bill;
    }

    public void setTotal_bill(String total_bill) {
        Total_bill = total_bill;
    }

    public String getPayment_status() {
        return Payment_status;
    }

    public void setPayment_status(String payment_status) {
        Payment_status = payment_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
        this.order_Id = order_Id;
    }

    @Override
    public String toString() {
        return "OrderObject{" +
                "orderItems=" + orderItems +
                ", userEmailId='" + userEmailId + '\'' +
                ", Total_bill='" + Total_bill + '\'' +
                ", Payment_status='" + Payment_status + '\'' +
                ", order_date='" + order_date + '\'' +
                ", order_Id='" + order_Id + '\'' +
                '}';
    }
}
