package com.hCue.Kiosk.services;

import retrofit.Callback;
import retrofit.RetrofitError;

public abstract class RestCallback<T> implements Callback<T> {

    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        try{
            RestError restError = (RestError) error.getBodyAs(RestError.class);
            if (restError != null &&
                    !("null".equalsIgnoreCase(String.valueOf(restError.getError())) &&
                            "null".equalsIgnoreCase(String.valueOf(restError.getErrorMessage()))
                            )
                    )
                failure(restError);
            else {
                failure(new RestError(error.getMessage()));
            }
        }
        catch(Exception e)
        {
            String ErrorMessage = e.getMessage();
            if(error != null)
            {
                ErrorMessage = !"".equalsIgnoreCase(error.getMessage()) ? error.getMessage() : e.getMessage();
            }
            failure(new RestError(ErrorMessage));
        }
    }
}
