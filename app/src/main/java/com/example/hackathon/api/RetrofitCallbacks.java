
package com.example.hackathon.api;

public interface RetrofitCallbacks {

    interface RetrofitErrorCallback {
        void onNetworkError(int code);
    }

    interface ObjectCallback {
        void objectOnResponse(String response);
    }


}
