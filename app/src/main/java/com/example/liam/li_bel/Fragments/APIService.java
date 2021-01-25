package com.example.liam.li_bel.Fragments;

import com.example.liam.li_bel.Notifications.MyResponse;
import com.example.liam.li_bel.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAATQCTzE:APA91bFVHVukiDGru6vu1APJ9uVxMfczwaQGQX5X8_KlHsonCtFMOWpdx-k_Gzs0TS7rTc-PSE51M8E0yDPwXeWZI-2E43DN8HfTJQiEqkDzPM14X7DfnHRZFEkfYjZRoAR6ODPCcHbt"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
