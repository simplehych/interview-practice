package com.peopeltech.interviewpractice.android.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.peopeltech.interviewpractice.util.Util;

/**
 * @author hych
 * @since 2020/4/18 10:13
 */
public class VolleyTest {

    private void test() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Util.getAppContext());
        String url = "http://www.baidu.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Util.log("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.log("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
