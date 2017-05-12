package com.mousebelly.app.userapp.products;

import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.NoProductsMessage;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class GetProductsData {

    public static HashMap<String, ProductUnit> productUnits = new HashMap<>();

    public static void convertDataToArrayList(String data) {

        //clear hashmap before reload
        productUnits.clear();

        if (data != null) {


            try {
                DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy:HHmm");
                Date getCurrentDate = new Date();
                String currentDate = dateFormat.format(getCurrentDate);

                String[] dateAndTime = currentDate.split(":");

                JSONArray hwfArray = new JSONArray(data);
                for (int i = 0; i < hwfArray.length(); i++) {
                    String hwf = hwfArray.getString(i);
                    System.out.println(hwf);

                    String mealplan = Server.s.get("http://mousebelly.herokuapp.com/mealplan/mealplan/" + hwf);
                    JSONArray mealPlanArray = new JSONArray(mealplan);
                    for (int j = 0; j < mealPlanArray.length(); j++) {
                        JSONObject jsonObject = mealPlanArray.getJSONObject(j);
                        System.out.println("Result is " + jsonObject);
                        String hwid = jsonObject.getString("housewife_id");
                        JSONObject product_plan = jsonObject.getJSONObject("product_plan").getJSONObject("f");
                        Iterator it = product_plan.keys();

                        while (it.hasNext()) {
                            String pro_id = (String) it.next();

                            JSONArray products = product_plan.getJSONArray(pro_id);
                            for (int k = 0; k < products.length(); k++) {
                                JSONObject item = products.getJSONObject(k);
                                String stime = item.getString("Start_Time");
                                String etime = item.getString("End_Time");
                                String date = item.getString("Date");

                                //check current products


                                if (dateAndTime[0].equals(date)) {
                                    if (Integer.parseInt(dateAndTime[1]) <= Integer.parseInt(etime)) {

                                        JSONObject product = item.getJSONObject("Food_Object");
                                        ProductUnit productUnit = new ProductUnit();

                                        JSONObject jsonObject1 = product.getJSONObject("HWFEmail_id");
                                        productUnit.setHWF_Name(jsonObject1.getString("HWF_Name"));
                                        productUnit.setHWFEmail_id(jsonObject1.getString("_id"));
                                        productUnit.setPhone_no(jsonObject1.getString("Phone_no"));

                                        productUnit.setImage(product.getString("Image"));
                                        productUnit.setPrice(product.getString("Price"));
                                        productUnit.set__v(product.getString("__v"));
                                        productUnit.set_id(product.getString("_id"));
                                        productUnit.setCountrate(product.getString("countrate"));
                                        productUnit.setDescription(product.getString("Description"));
                                        productUnit.setFeedback(product.getString("feedback"));
                                        productUnit.setIsApproved(product.getString("isApproved"));
                                        productUnit.setIsRejected(product.getString("isRejected"));
                                        productUnit.setProd_id(product.getString("Prod_id"));
                                        productUnit.setProd_category(product.getString("Prod_category"));
                                        productUnit.setProd_name(product.getString("Prod_name"));
                                        productUnit.setStarsize(product.getString("starsize"));

                                        GetProductsData.productUnits.put(productUnit.getProd_id(), productUnit);


                                    }
                                }
                            }
                        }
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            /*
            try {
                JSONArray jsonArray = new JSONArray(data);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ProductUnit productUnit = new ProductUnit();

                    JSONObject jsonObject1 = jsonObject.getJSONObject("HWFEmail_id");
                    productUnit.setHWF_Name(jsonObject1.getString("HWF_Name"));
                    productUnit.setHWFEmail_id(jsonObject1.getString("_id"));
                    productUnit.setPhone_no(jsonObject1.getString("Phone_no"));

                    productUnit.setImage(jsonObject.getString("Image"));
                    productUnit.setPrice(jsonObject.getString("Price"));
                    productUnit.set__v(jsonObject.getString("__v"));
                    productUnit.set_id(jsonObject.getString("_id"));
                    productUnit.setCountrate(jsonObject.getString("countrate"));
                    productUnit.setDescription(jsonObject.getString("Description"));
                    productUnit.setFeedback(jsonObject.getString("feedback"));
                    productUnit.setIsApproved(jsonObject.getString("isApproved"));
                    productUnit.setIsRejected(jsonObject.getString("isRejected"));
                    productUnit.setProd_id(jsonObject.getString("Prod_id"));
                    productUnit.setProd_category(jsonObject.getString("Prod_category"));
                    productUnit.setProd_name(jsonObject.getString("Prod_name"));
                    productUnit.setStarsize(jsonObject.getString("starsize"));

                    GetProductsData.productUnits.add(productUnit);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } else {
            System.out.println("Internet Connection : [LOST]");
        }
    }

    public static void loadData() {

        //remove views before reloading
        //MainActivity.productsLayout.removeAllViews();


        for (ProductUnit productUnit : GetProductsData.productUnits.values()) {
            System.out.println(productUnit);
            MainActivity.productsLayout.addView(productUnit.draw());
        }

        if (productUnits.isEmpty()) {

            RelativeLayout rl = NoProductsMessage.show(MainActivity.context, "Sorry! \n No Meal Available for Today");

            MainActivity.productsLayout.addView(rl);
        }

    }

}
