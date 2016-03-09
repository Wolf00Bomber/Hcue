package com.hCue.Kiosk.services;

import com.hCue.Kiosk.model.AddPatientRequest;
import com.hCue.Kiosk.model.AddPatientResponse;
import com.hCue.Kiosk.model.AdditionalInfoRequest;
import com.hCue.Kiosk.model.AdminGetDoctorsRequest;
import com.hCue.Kiosk.model.AdminGetDoctorsResponse;
import com.hCue.Kiosk.model.AdminLogin;
import com.hCue.Kiosk.model.AdminLoginRequest;
import com.hCue.Kiosk.model.AdminLoginResponse;
import com.hCue.Kiosk.model.CancelAppointmentRequest;
import com.hCue.Kiosk.model.CancelAppointmentResponse;
import com.hCue.Kiosk.model.ClinicResponse;
import com.hCue.Kiosk.model.ConfirmCancellationRequest;
import com.hCue.Kiosk.model.DoctorsAppointment;
import com.hCue.Kiosk.model.DoctorsAppointmentResponse;
import com.hCue.Kiosk.model.FeedbackRequest;
import com.hCue.Kiosk.model.GetDoctorAppointmentRequest;
import com.hCue.Kiosk.model.GetDoctorAppointmentResponse;
import com.hCue.Kiosk.model.GetDoctorRequest;
import com.hCue.Kiosk.model.GetDoctors;
import com.hCue.Kiosk.model.GetDoctorsResponse;
import com.hCue.Kiosk.model.GetPatientAppointmentsRequest;
import com.hCue.Kiosk.model.GetPatientAppointmentsResponse;
import com.hCue.Kiosk.model.GetPatientRequest;
import com.hCue.Kiosk.model.GetPatientResponse;
import com.hCue.Kiosk.model.Speciality;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

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
    void cancelAppointment(@Body CancelAppointmentRequest cancelAppointmentResponse, RestCallback<CancelAppointmentResponse> callback);


    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/validate/doctorLogin")
    void adminLogin(@Body AdminLoginRequest adminLoginRequest, RestCallback<AdminLoginResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/getDoctors")
    void getDoctors(@Body AdminGetDoctorsRequest adminGetDoctorsRequest, RestCallback<AdminGetDoctorsResponse> callback);


    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patient/DoctorRating")
    void postFeedback(@Body FeedbackRequest feedbackRequest, RestCallback<String> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patient/appointment/getPatientAppointments")
    void getPatientAppointment(@Body GetPatientAppointmentsRequest getPatientAppointmentsRequest,
                               RestCallback<GetPatientAppointmentsResponse> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/updtDoctorAppointmentsStatus")
    void confirmAppointmentCancellation(@Body ConfirmCancellationRequest confirmCancellationRequest,
                                        RestCallback<String> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/doctors/getDoctors")
    void getDoctor(@Body GetDoctorRequest getDoctorRequest, RestCallback<ClinicResponse> callback);
    @Headers({
            "Content-Type: application/json",
            "User-Agent: Hcue"
    })
    @POST("/patients/addPatient")
    void additionalInfo(@Body AdditionalInfoRequest additionalInfoRequest, RestCallback<AddPatientResponse> restCallback);
}
