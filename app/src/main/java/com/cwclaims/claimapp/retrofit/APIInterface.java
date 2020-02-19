package com.cwclaims.claimapp.retrofit;

import java.util.HashMap;
import java.util.Map;

import javax.sql.StatementEvent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface APIInterface {

    @FormUrlEncoded
    @POST("NewUser/AppSignup")
    Call<String> registerUser(@Field("name") String name, @Field("email") String email, @Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("Login")
    Call<String> login(@Field("email") String email, @Field("password") String password, @Field("device_token") String device_token, @Field("app_type") String app_type);

    @FormUrlEncoded
    @POST("Forget_password/Check_email")
    Call<String> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("Login/check_status")
    Call<String> checkStatus(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("Logout")
    Call<String> logout(@Field("user_id") String user_id, @Field("app_type") String app_type);

    @FormUrlEncoded
    @POST("Claimmate_report/Get_data")
    Call<String> getReport(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("Claimmate_report")
    Call<String> addReport(@Field("user_id") String user_id, @Field("user_name") String user_name, /*@Field("report") String report, */@Field("mr") String mr, @Field("claimant_name") String claimant_name, @Field("insuredNameDiffernt") String insuredNameDiffernt, @Field("insuredName") String insuredName, @Field("causesOfLoss") String causesOfLoss, @Field("dateLoss") String dateLoss, @Field("insuredPersonPresent") String insuredPersonPresent, @Field("isMortgagee") String isMortgagee, @Field("mortgagee") String mortgagee, @Field("isNoMortgagee") String isNoMortgagee, @Field("dateInspected") String dateInspected, @Field("timeInspected") String timeInspected, @Field("isRoof") String isRoof, @Field("pitch") String pitch, @Field("layers") String layers, @Field("edgeMetal") String edgeMetal, @Field("edgeMetalCustom") String edgeMetalCustom, @Field("type") String type, @Field("typeCustom") String typeCustom, @Field("age") String age, @Field("story") String story, @Field("dwl_first") String dwl_first, @Field("dwl_first_custom") String dwl_first_custom, @Field("dwl_second") String dwl_second, @Field("dwl_second_custom") String dwl_second_custom, @Field("dwl_third") String dwl_third, @Field("dwl_third_custom") String dwl_third_custom, @Field("dwl_fourth") String dwl_fourth, @Field("dwl_fourth_custom") String dwl_fourth_custom, @Field("dwl_fifth") String dwl_fifth, @Field("dwl_fifth_custom") String dwl_fifth_custom, @Field("dmg_interior") String dmg_interior, @Field("dmg_interior_custom") String dmg_interior_custom, @Field("dmg_roof") String dmg_roof, @Field("dmg_roof_custom") String dmg_roof_custom, @Field("dmg_front_eleva") String dmg_front_eleva, @Field("dmg_front_custom") String dmg_front_custom, @Field("dmg_left_eleva") String dmg_left_eleva, @Field("dmg_left_custom") String dmg_left_custom, @Field("dmg_back_eleva") String dmg_back_eleva, @Field("dmg_back_custom") String dmg_back_custom, @Field("dmg_right_eleva") String dmg_right_eleva, @Field("dmg_right_custom") String dmg_right_custom, @Field("dmg_notes") String dmg_notes, @Field("dmg_notes_custom") String dmg_notes_custom, @Field("misc_title") String misc_title, @Field("misc_title_custom") String misc_title_custom, @Field("misc_op") String misc_op, @Field("misc_depreciation") String misc_depreciation, @Field("misc_depreciation_year") String misc_depreciation_year, @Field("misc_aps_damage") String misc_aps_damage, @Field("misc_aps_damage_custom") String misc_aps_damage_custom, @Field("misc_contents") String misc_contents, @Field("misc_salvage") String misc_salvage, @Field("misc_salvage_custom") String misc_salvage_custom, @Field("LaborMin") String LaborMin, @Field("LaborMinAdded") String LaborMinAdded, @Field("LaborMinRemoved") String LaborMinRemoved, @Field("all") String all, @Field("AllCustom") String AllCustom);

    @FormUrlEncoded
    @POST("Claimmate_report")
    Call<String> addReport(@FieldMap Map<String, String> mapParam);

    @FormUrlEncoded
    @POST("Claimmate_report/add_3")
    Call<String> updateReport2(@FieldMap Map<String, String> mapParam);

    @FormUrlEncoded
    @POST("Claimmate_report/update")
    Call<String> updateReport(@Field("report_id") String report_id, @FieldMap Map<String, String> mapParam);

    @FormUrlEncoded
    @POST("Claimmate_report/delete")
    Call<String> deleteReport(@Field("report_id") String report_id, @Field("from") String from);
}
