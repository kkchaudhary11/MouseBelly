package com.mousebelly.app.userapp.feedback;

import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.products.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;


public class LoadOrdersForFeedback extends AsyncTask<Void, Void, Void> {

    String resp;
    Dialog feedbackDialog;
    Iterator it;
    float FeedbackThreshold = 3.8f;
    public static EditText OrderRatingEditText;


    //get orders
   /* public LoadOrdersForFeedback() {
        resp = Server.s.get("http://mousebelly.herokuapp.com/order/order2/" + USERID);
        GetOrderStack getOrderStackForFeedback = new GetOrderStack();
        getOrderStackForFeedback.loadData(resp);

        if (!GetOrderStack.feedorderStack.isEmpty()) {
            it = GetOrderStack.feedorderStack.keySet().iterator();
            showFeedback();
        }
    }
*/
    @Override
    protected Void doInBackground(Void... voids) {
        resp = Server.s.get("http://mousebelly.herokuapp.com/order/order2/" + USERID);
        GetOrderStack getOrderStackForFeedback = new GetOrderStack();
        getOrderStackForFeedback.loadData(resp);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (!GetOrderStack.feedorderStack.isEmpty()) {
            it = GetOrderStack.feedorderStack.keySet().iterator();
            showFeedback();
        }

    }


    String key;
    ArrayList<FeedFoodObjectInOrderStack> ProductsArrayList;
    List<Float> rating;


    // feeback module
    void showFeedback() {
        rating = new ArrayList<>();

        key = it.next().toString();
        ProductsArrayList = GetOrderStack.feedorderStack.get(key);

        //dialog box

        feedbackDialog = new Dialog(MainActivity.context);
        feedbackDialog.setTitle("Feedback");
        feedbackDialog.setContentView(R.layout.order_feedback_dialog_box);

        LinearLayout cartLinearLayout = (LinearLayout) feedbackDialog.findViewById(R.id.ProductsLinearLayout);

        TextView OrderIdtextView = (TextView) feedbackDialog.findViewById(R.id.order_id);

        TextView OrderRatingTextView = (TextView) feedbackDialog.findViewById(R.id.product_rating);

        OrderRatingEditText = (EditText) feedbackDialog.findViewById(R.id.review);

        OrderIdtextView.setText(key);

        Button submitFeedback = (Button) feedbackDialog.findViewById(R.id.dialogButtonOK);

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FeedFoodObjectInOrderStack.AverateRatingValue < FeedbackThreshold) {

                    if (OrderRatingEditText.getText().toString().matches("") && OrderRatingEditText.getText().toString().matches(".*\\w.*")) {
                        CustomToast.Toast(MainActivity.context, "Enter valid review");
                        return;
                    } else
                        sendtoServer();

                } else {

                    sendtoServer();
                }

            }
        });


        Button dialogButtonCancel = (Button) feedbackDialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackDialog.dismiss();

                clear();

                if (it.hasNext() == true) {
                    showFeedback();
                }
            }
        });

        //dilog box end


        for (FeedFoodObjectInOrderStack feedFoodObjectInOrderStack : ProductsArrayList) {

            // FeedFoodObjectInOrderStack feedFoodObjectInOrderStack = ProductsArrayList.get(i);

            rating.add(feedFoodObjectInOrderStack.getProductRating());

            FeedFoodObjectInOrderStack feedFoodObjectInOrderStack1 = new FeedFoodObjectInOrderStack();

            RelativeLayout rl = feedFoodObjectInOrderStack1.draw(feedFoodObjectInOrderStack);

            cartLinearLayout.addView(rl);

        }
        double res = calculateAverage(rating);
        //  double roundOff = (double) Math.round(res * 100) / 100;
        System.out.println("Average Rating : " + res);

        OrderRatingTextView.setText(String.valueOf(res));

        feedbackDialog.show();

    }


    private void sendtoServer() {
        System.out.println("Inside Submit");

        JSONObject feedbackJsonObject = new JSONObject();

        //get feedback id
        String fid = GenerateOrderId.getOrderId().replaceAll("[^0-9]", "");

        JSONObject products = new JSONObject();

        for (FeedFoodObjectInOrderStack f : ProductsArrayList) {

            rating.add(f.getOrderRating());

            JSONObject review = new JSONObject();
            try {
                review.put("pid", f.getPID());
                review.put("rating", f.getOrderRating());
                products.put(f.getPID(), review);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //setting feedback of individual product
            Server.s.put("http://mousebelly.herokuapp.com/prod_approval/prod_approvalstar/" + f.getPID() + "/" + f.getOrderRating());

        }

        //feedback done - true
        Server.s.put("http://mousebelly.herokuapp.com/order/feedbackDone/" + key + "/true");

        //send review also if average feedback is < 3.8
        if (FeedFoodObjectInOrderStack.AverateRatingValue < FeedbackThreshold) {

            try {
                feedbackJsonObject.put("uemail", LoginActivity.USERID);
                feedbackJsonObject.put("feedback", OrderRatingEditText.getText().toString());
                feedbackJsonObject.put("feed_id", "Feed" + fid);
                feedbackJsonObject.put("Order_id", key);
                feedbackJsonObject.put("Average", FeedFoodObjectInOrderStack.AverateRatingValue);
                feedbackJsonObject.put("products", products);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(feedbackJsonObject);

            Server.s.post("http://mousebelly.herokuapp.com/feed/feed", feedbackJsonObject);
        }

        feedbackDialog.dismiss();
        clear();
        if (it.hasNext() == true) {
            showFeedback();
        }

    }

    private void clear() {
        rating.clear();
        FeedFoodObjectInOrderStack.PRating.clear();
        FeedFoodObjectInOrderStack.AverateRatingValue = 0.0;
    }


    private double calculateAverage(List<Float> marks) {
        Float sum = 0f;
        if (!marks.isEmpty()) {
            for (Float mark : marks) {
                sum += mark;
            }
            double res = sum.doubleValue() / marks.size();
            return (double) Math.round(res * 100) / 100;
        }

        return sum;
    }

}