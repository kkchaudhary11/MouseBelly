package com.mousebelly.app.userapp.mealPlanner;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.NoProductsMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class MealPlan {
    public static HashMap<String, HashMap<String, HashMap<String, FoodObject>>> mealPlan = new HashMap();

    Context c;

    public MealPlan(Context c) {
        this.c = c;
    }

    public static void addToUI(FoodObject fobj) {
        MealPlannerMainActivity.productDataLayout.addView(fobj.draw());
        System.out.println("Meal Plan is empty : " + mealPlan.isEmpty());

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void loadData(String data) {


        this.c = c;
        try {

            DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
            Date getCurrentDate = new Date();


            Date currentDate = dateFormat.parse(dateFormat.format(getCurrentDate));

            JSONArray dataJsonArray = new JSONArray(data);
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject jsonObject = dataJsonArray.getJSONObject(i);
                System.out.println("Result is " + jsonObject);
                String hwid = jsonObject.getString("housewife_id");
                JSONObject product_plan = jsonObject.getJSONObject("product_plan").getJSONObject("f");
                Iterator it = product_plan.keys();

                HashMap hm3 = MealPlan.mealPlan.get(hwid);
                if (hm3 == null)
                    hm3 = new HashMap();

                while (it.hasNext()) {
                    String pro_id = (String) it.next();

                    HashMap hm2 = (HashMap) hm3.get(pro_id);

                    if (hm2 == null)
                        hm2 = new HashMap();


                    JSONArray products = product_plan.getJSONArray(pro_id);
                    for (int j = 0; j < products.length(); j++) {
                        JSONObject item = products.getJSONObject(j);
                        String stime = item.getString("Start_Time");
                        String etime = item.getString("End_Time");
                        String date = item.getString("Date");

                        //meal date
                        System.out.println("DATE :  " + date);
                        Date mealDate;
                        try {
                            mealDate = dateFormat.parse(date);
                        } catch (Exception e) {
                            continue;
                        }
                      /*  System.out.println("Current Date : " +currentDate);
                        System.out.println("Meal Date : " + mealDate);*/
                        if (currentDate.before(mealDate) || currentDate.equals(mealDate)) {


                            System.out.println("inside Date after");

                            JSONObject foodObj = item.getJSONObject("Food_Object");
                            FoodObject fobj = new FoodObject(this.c);
                            ///setting values.....
                            fobj.set_id(foodObj.getString("_id"));
                            fobj.setCountrate(foodObj.getString("countrate"));
                            fobj.setDate(date);
                            fobj.setProd_name(foodObj.getString("Prod_name"));
                            fobj.setDescription(foodObj.getString("Description"));
                            fobj.setEnd_Time(etime);
                            fobj.setFeedback(foodObj.getString("feedback"));
                            fobj.setHWF_Name(foodObj.getJSONObject("HWFEmail_id").getString("HWF_Name"));
                            fobj.setHWFEmail_id(foodObj.getJSONObject("HWFEmail_id").getString("_id"));
                            fobj.setBitmapImage(foodObj.getString("Image"));
                            fobj.setIsApproved(foodObj.getString("isApproved"));
                            fobj.setIsRejected(foodObj.getString("isRejected"));
                            fobj.setStart_Time(stime);
                            fobj.setPhone_no(foodObj.getJSONObject("HWFEmail_id").getString("Phone_no"));
                            fobj.setPrice(foodObj.getString("Price"));
                            fobj.setProd_category(foodObj.getString("Prod_category"));
                            fobj.setProd_name(foodObj.getString("Prod_name"));
                            fobj.setProd_id(pro_id);
                            fobj.setStarsize(foodObj.getString("starsize"));

                            hm2.put(date, fobj);
                            hm3.put(pro_id, hm2);

                            MealPlan.mealPlan.put(hwid, hm3);
                        }


                    }
                }
            }

            if (mealPlan.isEmpty()) {

                RelativeLayout rl = NoProductsMessage.show(MealPlannerMainActivity.context, "Sorry! \n No Meal Available");

                MealPlannerMainActivity.productDataLayout.addView(rl);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(MealPlan.mealPlan);
    }


}
