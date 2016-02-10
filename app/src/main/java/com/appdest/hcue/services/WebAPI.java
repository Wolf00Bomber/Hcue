package com.appdest.hcue.services;

import com.appdest.hcue.model.AdminLogin;
import com.appdest.hcue.model.DoctorsAppointment;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.model.GetPatientDetailsRequest;
import com.appdest.hcue.model.GetPatientDetailsResponse;
import com.appdest.hcue.model.GetHospitalsRequest;
import com.appdest.hcue.model.GetHospitalsResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface WebAPI {


    @POST("http://d318m5cseah7np.cloudfront.net/platformPartners/validate/adminLogin")
    void adminLogin(@Body AdminLogin adminLogin, RestCallback<String> callback);


    @POST("http://d1lmwj8jm5d3bc.cloudfront.net/patients/addPatient")
    void addPatient(@Body Object addPatient, RestCallback<Object> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctor/consultation/availableAppointment")
    void getDoctorAppointment(@Body GetDoctorAppointmentRequest getDoctorAppointmentRequest, RestCallback<GetDoctorAppointmentResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/addDoctorsAppointments")
    void addDoctorsAppointment(@Body DoctorsAppointment doctorsAppointment, RestCallback<DoctorsAppointmentResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patients/addPatient")
    void addPatient(@Body GetPatientDetailsRequest getPatientDetailsRequest, RestCallback<GetPatientDetailsResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/platformPartners/getHospitals")
    void getHospitalDetails(@Body GetHospitalsRequest doctorsAppointment, RestCallback<GetHospitalsResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @GET("/{DoctorID}/getDoctor")
    void getDoctorDetails(@Path("DoctorID") int doctorId, RestCallback<GetHospitalsResponse> callback);


}
