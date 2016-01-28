package com.appdest.hcue.services;

import com.appdest.hcue.model.AdminLogin;
import com.appdest.hcue.model.DoctorsAppointment;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public interface WebAPI {


    @POST("http://d318m5cseah7np.cloudfront.net/platformPartners/validate/adminLogin")
    void adminLogin(@Body AdminLogin adminLogin, RestCallback<String> callback);

    @POST("http://dct4avjn1lfw.cloudfront.net/doctors/addDoctorsAppointments")
    void addDoctorsAppointment(@Body DoctorsAppointment doctorsAppointment, RestCallback<DoctorsAppointmentResponse> callback);

    @POST("http://d1lmwj8jm5d3bc.cloudfront.net/patients/addPatient")
    void addPatient(@Body Object addPatient, RestCallback<Object> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctor/consultation/availableAppointment")
    void getDoctorAppointment(@Body GetDoctorAppointmentRequest getDoctorAppointmentRequest, RestCallback<GetDoctorAppointmentResponse> callback);

}
