package com.appdest.hcue.services;

import com.appdest.hcue.model.AddPatientRequest;
import com.appdest.hcue.model.AddPatientResponse;
import com.appdest.hcue.model.AdminLogin;
import com.appdest.hcue.model.AdminLoginRequest;
import com.appdest.hcue.model.AdminLoginResponse;
import com.appdest.hcue.model.DoctorsAppointment;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.model.GetDoctors;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientDetailsRequest;
import com.appdest.hcue.model.GetPatientDetailsResponse;
import com.appdest.hcue.model.GetPatientRequest;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.model.Speciality;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface WebAPI {


    @POST("http://d318m5cseah7np.cloudfront.net/platformPartners/validate/adminLogin")
    void adminLogin(@Body AdminLogin adminLogin, RestCallback<String> callback);

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

    /*@Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patients/addPatient")
    void addPatient(@Body GetPatientDetailsRequest getPatientDetailsRequest, RestCallback<GetPatientDetailsResponse> callback);*/

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/getDoctors")
    void getDoctors(@Body GetDoctors listDoctorsRequest, RestCallback<GetDoctorsResponse> callback);

    /*@Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @GET("/{DoctorID}/getDoctor")
    void getDoctorDetails(@Path("DoctorID") int doctorId, RestCallback<GetHospitalsResponse> callback);*/

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @GET("/doctor/readSpecialityType/ALL")
    void getSpecialityMap(RestCallback<List<Speciality>> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patients/addPatient")
    void addPatient(@Body AddPatientRequest listDoctorsRequest, RestCallback<AddPatientResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patients/getPatients")
    void getPatients(@Body GetPatientRequest getPatientRequest, RestCallback<GetPatientResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patient/appointment/getPatientAppointments")
    void cancelAppointment(@Body GetPatientRequest getPatientRequest, RestCallback<GetPatientResponse> callback);


    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/validate/doctorLogin")
    void adminLogin(@Body AdminLoginRequest adminLoginRequest, RestCallback<AdminLoginResponse> callback);
}
