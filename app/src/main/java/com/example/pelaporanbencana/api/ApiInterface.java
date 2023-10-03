package com.example.pelaporanbencana.api;

import com.example.pelaporanbencana.model.AffectedShowAllAsId.AffectedShowAllAsIdResponse;
import com.example.pelaporanbencana.model.AffectedStore.AffectedStoreResponse;
import com.example.pelaporanbencana.model.DamageCategoryiShowall.DamageCategoryResponse;
import com.example.pelaporanbencana.model.DamageShowIdMax.DamageShowIdMaxResponse;
import com.example.pelaporanbencana.model.DamageShowa.DamageShowaResponse;
import com.example.pelaporanbencana.model.DamageShowall.DamageShowallResponse;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.DevDisastersShowall.DevDisastersShowallResponse;
import com.example.pelaporanbencana.model.DevDisastersStore.DevDisastersStoreResponse;
import com.example.pelaporanbencana.model.Disaster.DisasterResponse;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
import com.example.pelaporanbencana.model.DisasterStore.DisasterStoreResponse;
import com.example.pelaporanbencana.model.DisasterUserShowConstribution.ShowContributionResponse;
import com.example.pelaporanbencana.model.EvacueeShowall.EvacueeShowallResponse;
import com.example.pelaporanbencana.model.EvacueeStore.EvacueeStoreResponse;
import com.example.pelaporanbencana.model.FacilityShowAll.FacilityShowallResponse;
import com.example.pelaporanbencana.model.FacilityShowa.FacilityShowaResponse;
import com.example.pelaporanbencana.model.FacilityStore.FacilityStoreResponse;
import com.example.pelaporanbencana.model.Login.LoginResponse;
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.example.pelaporanbencana.model.PeopleStore.PeopleStoreResponse;
import com.example.pelaporanbencana.model.PicDisastersShowall.PicDisastersShowAllResponse;
import com.example.pelaporanbencana.model.PicturesDisastersStore.PicturesDisastersStoreResponse;
import com.example.pelaporanbencana.model.ResourceShowa.ResourcesShowaResponse;
import com.example.pelaporanbencana.model.ResourcesShowAll.ResourcesShowAllResponse;
import com.example.pelaporanbencana.model.ResourcesStore.ResourcesStoreResponse;
import com.example.pelaporanbencana.model.ShelterShowAll.ShelterShowAllResponse;
import com.example.pelaporanbencana.model.ShelterShowa.ShelterShowaResponse;
import com.example.pelaporanbencana.model.ShelterShowallAsId.ShelterShowallAsIdResponse;
import com.example.pelaporanbencana.model.ShelterStore.ShelterStoreResponse;
import com.example.pelaporanbencana.model.SocAssistDistributed.SocAssistDistributedResponse;
import com.example.pelaporanbencana.model.SocAssistDistributedShowAll.SocAssistDistributedShowAllResponse;
import com.example.pelaporanbencana.model.SocAssistDistributedShowa.SocAssistDistributedShowaResponse;
import com.example.pelaporanbencana.model.SocAssistShowaResponse.SocAssistShowaResponse;
import com.example.pelaporanbencana.model.SocialAssistanceShowall.SocAssitShowAllResponse;
import com.example.pelaporanbencana.model.SocialAssistanceStore.SocAssitStoreResponse;
import com.example.pelaporanbencana.model.UrbanVillage.UrbanVillageResponse;
import com.example.pelaporanbencana.model.UserLocStore.UserLocStoreResponse;
import com.example.pelaporanbencana.model.VictimShowAllAsId.VictimShowAllAsIdResponse;
import com.example.pelaporanbencana.model.VictimShowa.VictimShowOneResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.example.pelaporanbencana.model.VolunteerOrgShowa.VolunteerOrgShowaResponse;
import com.example.pelaporanbencana.model.VolunteerOrgStore.VolunteerOrgStoreResponse;
import com.example.pelaporanbencana.model.VolunteerOrganization.VolunteerOrgShowallResponse;
import com.example.pelaporanbencana.model.VolunteerShowa.VolunteerShowaResponse;
import com.example.pelaporanbencana.model.VolunteerShowa1.VolunteerShowa1Response;
import com.example.pelaporanbencana.model.VolunteerShowall.VolunteerShowallResponse;
import com.example.pelaporanbencana.model.VolunteerStore.VolunteerStoreResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("disaster_user/store")
    Call<UserLocStoreResponse> setUserLoc(
            @Header("Authorization") String accessToken,
            @Field("id_user") int id_user,
            @Field("id_disaster") String id_disaster,
            @Field("user_lat") String user_lat,
            @Field("user_long") String user_long,
            @Field("location") String location,
            @Field("date") String date
    );

    @Headers("Accept:application/json")
    @GET("disaster/showall")
    Call<DisasterResponse> getDisaster(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @Headers("Accept:application/json")
    @GET("urban_village/showall")
    Call<UrbanVillageResponse> getUrbanVillage(
            @Header("Authorization") String accessToken
    );

    @Headers("Accept:application/json")
    @GET("disasters_pictures/showall")
    Call<PicDisastersShowAllResponse> getPicDisaster(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @Headers("Accept:application/json")
    @GET("disaster_user/showcontribution")
    Call<ShowContributionResponse> getShowContribution(
            @Header("Authorization") String accessToken,
            @Query("id") int id_user
    );

    @Multipart
    @POST("disasters_pictures/store")
    Call<PicturesDisastersStoreResponse> setPicDisaster(
            @Header("Authorization") String accessToken,
            @Part("id_pictures") RequestBody id_pictures,
            @Part("id_disasters") RequestBody id_disasters,
            @Part MultipartBody.Part pictures
    );

//    @Multipart
//    @POST("disasters_pictures/store")
//    Call<PicturesDisastersStoreResponse> setPicDisaster(
//            @Header("Authorization") String accessToken,
//            @Field("id_pictures") String id_pictures,
//            @Field("id_disasters") String id_disasters,
//            @Part MultipartBody.Part pictures
//    );

//    @FormUrlEncoded
//    @POST("dev_of_disasters/store")
//    Call<DisasterStoreResponse> setDevOfDisaster(
//            @Header("Authorization") String accessToken,
//            @Query("id_disasters") String id_disasters1,
//            @Query("disasters_date") String disasters_date1,
//            @Query("disasters_time") String disasters_time1,
//            @Field("id_disasters") String id_disasters,
//            @Field("id_urban_village") String id_urban_village,
//            @Field("disasters_types") String disasters_types,
//            @Field("disasters_date") String disasters_date,
//            @Field("disasters_time") String disasters_time,
//            @Field("disasters_long") String disasters_long,
//            @Field("disasters_lat") String disasters_lat,
//            @Field("disasters_village") String disasters_village,
//            @Field("disasters_status") String disasters_status
//    );

    @FormUrlEncoded
    @POST("dev_of_disasters/store")
    Call<DevDisastersStoreResponse> setDevDisaster(
            @Header("Authorization") String accessToken,
            @Field("id_disasters") String id_disasters,
            @Field("disasters_date") String disasters_date,
            @Field("disasters_time") String disasters_time,
            @Field("disasters_desc") String disasters_desc,
            @Field("disasters_impact") String disasters_impact,
            @Field("disasters_causes") String disasters_causes,
            @Field("weather_conditions") String weather_conditions,
            @Field("disasters_potential") String disasters_potential,
            @Field("disasters_effort") String disasters_effort

    );

    @FormUrlEncoded
    @POST("dev_of_disasters/update")
    Call<DevDisastersStoreResponse> updateDevOfDisaster(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters1,
            @Query("disasters_date") String disasters_date1,
            @Query("disasters_time") String disasters_time1,
            @Field("id_disasters") String id_disasters,
            @Field("disasters_date") String disasters_date,
            @Field("disasters_time") String disasters_time,
            @Field("disasters_desc") String disasters_desc,
            @Field("disasters_impact") String disasters_impact,
            @Field("disasters_causes") String disasters_causes,
            @Field("weather_conditions") String weather_conditions,
            @Field("disasters_potential") String disasters_potential,
            @Field("disasters_effort") String disasters_effort
    );

    @Headers("Accept:application/json")
    @GET("dev_of_disasters/showall")
    Call<DevDisastersShowallResponse> getDevDisaster(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters,
            @Query("disasters_date") String disasters_date,
            @Query("disasters_time") String disasters_time
    );

    @POST("dev_of_disasters/destroy")
    Call<DevDisastersStoreResponse> deleteDevDisaster(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("disaster/store")
    Call<DisasterStoreResponse> setDisaster(
            @Header("Authorization") String accessToken,
            @Field("id_disasters") String id_disasters,
            @Field("id_urban_village") String id_urban_village,
            @Field("disasters_types") String disasters_types,
            @Field("disasters_date") String disasters_date,
            @Field("disasters_time") String disasters_time,
            @Field("disasters_long") String disasters_long,
            @Field("disasters_lat") String disasters_lat,
            @Field("disasters_village") String disasters_village,
            @Field("disasters_desc") String disasters_desc,
            @Field("disasters_status") String disasters_status

    );

    @FormUrlEncoded
    @POST("disaster/update")
    Call<DisasterStoreResponse> updateDisaster(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters1,
            @Field("id_disasters") String id_disasters,
            @Field("id_urban_village") String id_urban_village,
            @Field("disasters_types") String disasters_types,
            @Field("disasters_date") String disasters_date,
            @Field("disasters_time") String disasters_time,
            @Field("disasters_long") String disasters_long,
            @Field("disasters_lat") String disasters_lat,
            @Field("disasters_village") String disasters_village,
            @Field("disasters_status") String disasters_status
    );


    @Headers("Accept:application/json")
    @GET("disaster/showinmenu")
    Call<DisInMenusResponse> getDisasterInMenu(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @FormUrlEncoded
    @POST("damage/store")
    Call<DamageStoreResponse> setDamage(
            @Header("Authorization") String accessToken,
            @Field("id_damages") int id_damages,
            @Field("id_damage_category") String id_damage_category,
            @Field("id_disasters") String id_disasters,
            @Field("damage_name") String damage_name,
            @Field("damage_types") String damage_types,
            @Field("damage_amount") int damage_amount,
            @Field("damage_units") String damage_units

    );

    @Headers("Accept:application/json")
    @GET("damage_category/showall")
    Call<DamageCategoryResponse> getDamageCategory(
            @Header("Authorization") String accessToken
    );

    @FormUrlEncoded
    @POST("damage/update")
    Call<DamageStoreResponse> setDamageUpdate(
            @Header("Authorization") String accessToken,
            @Query("id_damages") int id_damages1,
            @Query("id_damage_category") String id_damage_category1,
            @Query("id_disasters") String id_disasters1,
            @Query("damage_types") String damage_types1,
            @Field("id_damages") int id_damages,
            @Field("id_damage_category") String id_damage_category,
            @Field("id_disasters") String id_disasters,
            @Field("damage_types") String damage_types,
            @Field("damage_name") String damage_name,
            @Field("damage_amount") int damage_amount,
            @Field("damage_units") String damage_units

    );

//    @FormUrlEncoded
//    @POST("damage/update")
//    Call<DamageStoreResponse> setDamageUpdate(
//            @Header("Authorization") String accessToken,
//            @Query("id_damage_category") String id_damage_category1,
//            @Query("id_disasters") String id_disasters1,
//            @Query("damage_types") String damage_types1,
//            @Field("id_damage_category") String id_damage_category,
//            @Field("id_disasters") String id_disasters,
//            @Field("damage_types") String damage_types,
//            @Field("damage_amount") int damage_amount,
//            @Field("damage_units") String damage_units
//
//    );

    @POST("damage/destroy")
    Call<DamageStoreResponse> deleteDamage(
            @Header("Authorization") String accessToken,
            @Query("id_damage") String id_damage
    );

    @Headers("Accept:application/json")
    @GET("damage/showall")
    Call<DamageShowallResponse> getDamageShowAll(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @Headers("Accept:application/json")
    @GET("damage/showa")
    Call<DamageShowaResponse> getDamageShowOne(
            @Header("Authorization") String accessToken,
            @Query("id_damage") String id_damage
    );

    @Headers("Accept:application/json")
    @GET("damage/showaIdDamage")
    Call<DamageShowIdMaxResponse> getIdDamageShow(
            @Header("Authorization") String accessToken,
            @Query("id_damage_category") String id_damage_category,
            @Query("id_disasters") String id_disasters
    );


    @FormUrlEncoded
    @POST("shelter/store")
    Call<ShelterStoreResponse> setShelter(
            @Header("Authorization") String accessToken,
            @Field("id_shelter") String id_shelter,
            @Field("long_loc") String long_loc,
            @Field("lat_loc") String lat_loc,
            @Field("capacity") int capacity,
            @Field("hunian_types") String hunian_types,
            @Field("location") String location,
            @Field("address") String address

    );

    @FormUrlEncoded
    @POST("shelter/update")
    Call<ShelterStoreResponse> updateShelter(
            @Header("Authorization") String accessToken,
            @Query("id_shelter") String id_shelter1,
            @Field("id_shelter") String id_shelter,
            @Field("long_loc") String long_loc,
            @Field("lat_loc") String lat_loc,
            @Field("capacity") int capacity,
            @Field("hunian_types") String hunian_types,
            @Field("location") String location,
            @Field("address") String address

    );

    @POST("shelter/destroy")
    Call<ShelterStoreResponse> deleteShelter(
            @Header("Authorization") String accessToken,
            @Query("id_shelter") String id_shelter
    );

    @Headers("Accept:application/json")
    @GET("shelter/showallasid")
    Call<ShelterShowallAsIdResponse> getShelterShowAllAsId(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @Headers("Accept:application/json")
    @GET("shelter/showa")
    Call<ShelterShowaResponse> getShelterShowa(
            @Header("Authorization") String accessToken,
            @Query("id_shelter") String id_shelter
    );

    @Headers("Accept:application/json")
    @GET("shelter/showall")
    Call<ShelterShowAllResponse> getShelterShowAll(
            @Header("Authorization") String accessToken
    );

    @FormUrlEncoded
    @POST("sa_distributed/store")
    Call<SocAssistDistributedResponse> setSocialAssistDistributed(
            @Header("Authorization") String accessToken,
            @Field("id_sa_types") int id_sa_types,
            @Field("id_disasters") String id_disasters,
            @Field("recipient") String recipient,
            @Field("date_sent") String date_sent,
            @Field("sa_distributed_amount") int sa_distributed_amount,
            @Field("sa_distributed_units") String sa_distributed_units,
            @Field("batch") int batch

    );

    @FormUrlEncoded
    @POST("sa_distributed/update")
    Call<SocAssistDistributedResponse> updateSocialAssistDistributed(
            @Header("Authorization") String accessToken,
            @Query("id_sa_types") int id_sa_types1,
            @Query("id_disasters") String id_disasters1,
            @Query("batch") int batch1,
            @Field("id_sa_types") int id_sa_types,
            @Field("id_disasters") String id_disasters,
            @Field("recipient") String recipient,
            @Field("date_sent") String date_sent,
            @Field("sa_distributed_amount") int sa_distributed_amount,
            @Field("sa_distributed_units") String sa_distributed_units,
            @Field("batch") int batch

    );

    @Headers("Accept:application/json")
    @GET("sa_distributed/showall")
    Call<SocAssistDistributedShowAllResponse> getSocialAssistDistributed(
            @Header("Authorization") String accessToken,
            @Query("id") String id

    );

    @Headers("Accept:application/json")
    @GET("sa_distributed/showa")
    Call<SocAssistDistributedShowaResponse> getOneSaDistributed(
            @Header("Authorization") String accessToken,
            @Query("id_sa_types") int id_sa_types,
            @Query("id_disasters") String id_disasters,
            @Query("batch") int batch

    );

    @POST("sa_distributed/destroy")
    Call<SocAssistDistributedResponse> deleteSaDistributed(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("social_assistance/store")
    Call<SocAssitStoreResponse> setSocialAssistance(
                    @Header("Authorization") String accessToken,
                    @Field("id_sa_types") int id_sa_types,
                    @Field("id_disasters") String id_disasters,
                    @Field("donor") String donor,
                    @Field("date_received") String date_received,
                    @Field("social_assistance_amount") int social_assistance_amount,
                    @Field("social_assistance_unit") String social_assistance_unit,
                    @Field("batch") int batch

    );

    @Headers("Accept:application/json")
    @GET("social_assistance/showall")
    Call<SocAssitShowAllResponse> getSocialAssistance(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("social_assistance/update")
    Call<SocAssitStoreResponse> updateSocialAssistance(
            @Header("Authorization") String accessToken,
            @Query("id_sa_types") int id_sa_types1,
            @Query("id_disasters") String id_disasters1,
            @Query("batch") int batch1,
            @Field("id_sa_types") int id_sa_types,
            @Field("id_disasters") String id_disasters,
            @Field("donor") String donor,
            @Field("date_received") String date_received,
            @Field("social_assistance_amount") int social_assistance_amount,
            @Field("social_assistance_unit") String social_assistance_unit,
            @Field("batch") int batch

    );

    @POST("social_assistance/destroy")
    Call<SocAssitStoreResponse> deleteSocialAssistance(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @Headers("Accept:application/json")
    @GET("social_assistance/showa")
    Call<SocAssistShowaResponse> getOneSocialAssistance(
            @Header("Authorization") String accessToken,
            @Query("id_sa_types") String id_sa_types,
            @Query("id_disasters") String id_disasters,
            @Query("batch") String batch
    );

    @FormUrlEncoded
    @POST("facility/store")
    Call<FacilityStoreResponse> setFacility(
            @Header("Authorization") String accessToken,
            @Field("id_facilities") int id_facilities,
            @Field("id_disasters") String id_disasters,
            @Field("description") String description
    );

    @POST("facility/destroy")
    Call<FacilityStoreResponse> deleteFacility(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("facility/update")
    Call<FacilityStoreResponse> updateFacility(
            @Header("Authorization") String accessToken,
            @Query("id_facilities") int id_facilities1,
            @Query("id_disasters") String id_disasters1,
            @Field("id_facilities") int id_facilities,
            @Field("id_disasters") String id_disasters,
            @Field("description") String description
    );

    @Headers("Accept:application/json")
    @GET("facility/showall")
    Call<FacilityShowallResponse> getFacility(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @Headers("Accept:application/json")
    @GET("facility/showa")
    Call<FacilityShowaResponse> getOneFacility(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters,
            @Query("id_facilities") String id_facilities
    );

    @FormUrlEncoded
    @POST("resources/store")
    Call<ResourcesStoreResponse> setResources(
            @Header("Authorization") String accessToken,
            @Field("id_disasters") String id_disasters,
            @Field("id_resources") int id_resources,
            @Field("resources_available") int resources_available,
            @Field("resources_required") int resources_required,
            @Field("lack_of_resources") int lack_of_resources,
            @Field("additional_info") String additional_info
    );

    @FormUrlEncoded
    @POST("resources/update")
    Call<ResourcesStoreResponse> updateResources(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters1,
            @Query("id_resources") int id_resources1,
            @Query("id_disaster_resources") int id_dr_int,
            @Field("id_disasters") String id_disasters,
            @Field("id_resources") int id_resources,
            @Field("resources_available") int resources_available,
            @Field("resources_required") int resources_required,
            @Field("lack_of_resources") int lack_of_resources,
            @Field("additional_info") String additional_info
    );

    @Headers("Accept:application/json")
    @GET("resources/showallasid")
    Call<ResourcesShowAllResponse> getResources(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @Headers("Accept:application/json")
    @GET("resources/showa")
    Call<ResourcesShowaResponse> getOneResources(
            @Header("Authorization") String accessToken,
            @Query("id_resources") int id_resources,
            @Query("id_disasters") String id_disasters,
            @Query("id_disaster_resources") int id_dr_int

    );

    @POST("resources/destroy")
    Call<ResourcesStoreResponse> deleteResources(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("evacuee/store")
    Call<EvacueeStoreResponse> setEvacuee(
            @Header("Authorization") String accessToken,
            @Field("nik") String nik,
            @Field("id_disasters") String id_disasters,
            @Field("id_shelter") String id_shelter,
            @Field("name") String name,
            @Field("address") String address,
            @Field("gender") String gender,
            @Field("heir") String heir,
            @Field("picture") String picture,
            @Field("birthdate") String birthdate
    );

    @Headers("Accept:application/json")
    @GET("people/showa")
    Call<PeopleOneShowResponse> getPeople(
            @Header("Authorization") String accessToken,
            @Query("id") String nik
    );

    @FormUrlEncoded
    @POST("people/update")
    Call<PeopleStoreResponse> updatePeople(
            @Header("Authorization") String accessToken,
            @Query("nik") String nik1,
            @Field("nik") String nik,
            @Field("name") String name,
            @Field("address") String address,
            @Field("gender") String gender,
            @Field("heir") String heir,
            @Field("birthdate") String birthdate
    );

    @Headers("Accept:application/json")
    @GET("evacuee/showall")
    Call<EvacueeShowallResponse> getEvacueeShowall(
            @Header("Authorization") String accessToken,
            @Query("id_disasters") String id_disasters,
            @Query("id_shelter") String id_shelter
    );

    @POST("evacuee/destroy")
    Call<EvacueeStoreResponse> deleteEvacuee(
            @Header("Authorization") String accessToken,
            @Query("id_evacuee") String id_evacuee
    );

    @Headers("Accept:application/json")
    @GET("victim/showa")
    Call<VictimShowOneResponse> getOneVictim(
            @Header("Authorization") String accessToken,
            @Query("nik") String nik,
            @Query("id_disasters") String id_disasters
    );

    @Headers("Accept:application/json")
    @GET("victim/showallasid")
    Call<VictimShowAllAsIdResponse> getVictimShowAllAsId(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @FormUrlEncoded
    @POST("victim/store")
    Call<VictimStoreResponse> setVictim(
            @Header("Authorization") String accessToken,
            @Field("nik") String nik,
            @Field("id_disasters") String id_disasters,
            @Field("victims_status") String victims_status,
            @Field("medical_status") String medical_status,
            @Field("hospital") String hospital,
            @Field("additional_info") String additional_info,
            @Field("name") String name,
            @Field("address") String address,
            @Field("gender") String gender,
            @Field("heir") String heir,
            @Field("birthdate") String birthdate
    );

    @FormUrlEncoded
    @POST("victim/update")
    Call<VictimStoreResponse> updateVictim(
            @Header("Authorization") String accessToken,
            @Field("id_victims") String id_victims,
            @Field("nik") String nik,
            @Field("id_disasters") String id_disasters,
            @Field("victims_status") String victims_status,
            @Field("medical_status") String medical_status,
            @Field("hospital") String hospital,
            @Field("additional_info") String additional_info
    );

    @POST("victim/destroy")
    Call<VictimStoreResponse> deleteVictim(
            @Header("Authorization") String accessToken,
            @Query("id_victims") String id_victims
    );

    @FormUrlEncoded
    @POST("affected/store")
    Call<AffectedStoreResponse> setAffected(
            @Header("Authorization") String accessToken,
            @Field("nik") String nik,
            @Field("id_disasters") String id_disasters,
            @Field("name") String name,
            @Field("address") String address,
            @Field("gender") String gender,
            @Field("heir") String heir,
            @Field("birthdate") String birthdate
    );

    @POST("affected/destroy")
    Call<AffectedStoreResponse> deleteAffected(
            @Header("Authorization") String accessToken,
            @Query("id_affected") String id_affected
    );

    @Headers("Accept:application/json")
    @GET("affected/showallasid")
    Call<AffectedShowAllAsIdResponse> getAffectedShowAllAsId(
            @Header("Authorization") String accessToken,
            @Query("id") String id_disasters
    );

    @Headers("Accept:application/json")
    @GET("volunteer_organization/showall")
    Call<VolunteerOrgShowallResponse> getVolunteerOrg(
            @Header("Authorization") String accessToken
    );

    @Headers("Accept:application/json")
    @GET("volunteer/showall")
    Call<VolunteerShowallResponse> getVolunteer(
            @Header("Authorization") String accessToken,
            @Query("id1") String id_disasters,
            @Query("id2") String id_volunteer_org
    );

    @Headers("Accept:application/json")
    @GET("volunteer/showa")
    Call<VolunteerShowaResponse> getOneVolunteer(
            @Header("Authorization") String accessToken,
            @Query("id1") String id_volunteer,
            @Query("id2") String id_volunteer_org
    );

    @Headers("Accept:application/json")
    @GET("volunteer/showa1")
    Call<VolunteerShowa1Response> getOneVolunteer1(
            @Header("Authorization") String accessToken,
            @Query("id1") String id_volunteer,
            @Query("id2") String id_disasters
    );

    @FormUrlEncoded
    @POST("volunteer/store")
    Call<VolunteerStoreResponse> setVolunteer(
            @Header("Authorization") String accessToken,
            @Field("id_volunteers") String id_volunteers,
            @Field("id_volunteer_org") String id_volunteer_org,
            @Field("volunteers_name") String volunteers_name,
            @Field("volunteers_birthdate") String volunteers_birthdate,
            @Field("volunteers_gender") String volunteers_gender,
            @Field("volunteers_skill") String volunteers_skill,
            @Field("id_disasters") String id_disasters,
            @Field("id_volunteer") String id_volunteer,
            @Field("placement") String placement,
            @Field("assignment") String assignment
    );

    @FormUrlEncoded
    @POST("volunteer/update")
    Call<VolunteerStoreResponse> updateVolunteer(
            @Header("Authorization") String accessToken,
            @Query("id_volunteers") String id_volunteers1,
            @Field("id_volunteers") String id_volunteers,
            @Field("id_volunteer_org") String id_volunteer_org,
            @Field("volunteers_name") String volunteers_name,
            @Field("volunteers_birthdate") String volunteers_birthdate,
            @Field("volunteers_gender") String volunteers_gender,
            @Field("volunteers_skill") String volunteers_skill,
            @Field("id_disasters") String id_disasters,
            @Field("id_volunteer") String id_volunteer,
            @Field("placement") String placement,
            @Field("assignment") String assignment
    );

    @POST("volunteer/destroy")
    Call<VolunteerStoreResponse> deleteVolunteer(
            @Header("Authorization") String accessToken,
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("volunteer_organization/store")
    Call<VolunteerOrgStoreResponse> setVolunteerOrg(
            @Header("Authorization") String accessToken,
            @Field("id_volunteer_org") String id_volunteer_org,
            @Field("volunteer_org_name") String volunteers_name,
            @Field("volunteer_org_address") String volunteers_birthdate,
            @Field("volunteer_org_status") String volunteers_gender
    );

    @FormUrlEncoded
    @POST("volunteer_organization/update")
    Call<VolunteerOrgStoreResponse> updateVolunteerOrg(
            @Header("Authorization") String accessToken,
            @Query("id_volunteer_org") String id_volunteer_org1,
            @Field("id_volunteer_org") String id_volunteer_org,
            @Field("volunteer_org_name") String volunteers_name,
            @Field("volunteer_org_address") String volunteers_birthdate,
            @Field("volunteer_org_status") String volunteers_gender
    );

    @Headers("Accept:application/json")
    @GET("volunteer_organization/showa")
    Call<VolunteerOrgShowaResponse> getOneVolunteerOrg(
            @Header("Authorization") String accessToken,
            @Query("id") String id_volunteer_org
    );

}
