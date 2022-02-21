package ru.biis.biissale.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.biis.biissale.Authbiis;
import ru.biis.biissale.Callbiis;
import ru.biis.biissale.Catsbiis;
import ru.biis.biissale.Detailsbiis;
import ru.biis.biissale.Infobiis;
import ru.biis.biissale.Messagesbiis;
import ru.biis.biissale.NotifCallsbiis;
import ru.biis.biissale.Notifbiis;
import ru.biis.biissale.Removebiis;

public interface TooltipsApi {

    @GET("biis_send_notification/")
    Call<List<Notifbiis>> notification(@Query("userid") String useridQuery);

    @GET("biis_send_notification_calls/")
    Call<List<NotifCallsbiis>> notificationCalls(@Query("userid") String useridQuery);

    @GET("biis_send_notification_remove/")
    Call<Removebiis> notificationRemove(@Query("autoid") String autoidQuery);

    @GET("biis_set_call/")
    Call<List<Catsbiis>> addCall(@Query("name") String nameQuery, @Query("cats") String catsQuery);

    @GET("biis_get_auth_partner/")
    Call<Authbiis> auth(@Query("login") String loginQuery, @Query("pass") String passQuery);

    @GET("Biis_get_auth")
    Call<Authbiis> authV1(@Query("login") String loginQuery, @Query("pass") String passQuery);

    @GET("biis_get_callbacks_partner/")// заменить
    Call<List<Callbiis>> callsPartner(@Query("userid") String loginQuery);

    @GET("getrequestforseller/")// замена для biis_get_callbacks_partner/
    Call<List<Callbiis>> callsPartnerGuid(@Query("sellerId") String loginQuery);

    @GET("biis_get_detailinfo_partner/")
    Call<Infobiis> infoPartner(@Query("userid") String loginQuery);

    @GET("biis_get_reg_partner/")
    Call<Authbiis> reg(@Query("login") String loginQuery);

    @GET("biis_get_calldetails/")
    Call<List<Detailsbiis>> details(@Query("callid") String idQuery);

    //   @GET("biis_get_callmessages_partner/")
    //   Call<List<Messagesbiis>> dialogPartner(
//            @Query("userid") String idrQuery,
    //           @Query("parent") String parentQuery,
    //           @Query("callid") String callcQuery
    //   );

    @GET("GetUserMessages/")
    Call<List<Messagesbiis>> dialogPartner(
            @Query("userid") String UseridQuery,
            @Query("requestid") String requestQuery
    );

    @GET("biis_set_messages_partner/")
    Call<List<Messagesbiis>> setmsg(
            @Query("responseid") String idrQuery,
            @Query("callid") String idcQuery,
            @Query("msg") String msgQuery,
            @Query("userid") String useridQuery,
            @Query("price") String priceQuery
    );

    @GET("biis_set_info_partner/")
    Call<List<Messagesbiis>> setinfo(
            @Query("getname") String nameQuery,
            @Query("getemail") String emailQuery,
            @Query("getphone") String phoneQuery,
            @Query("getgetaddress") String addressQuery,
            @Query("userid") String useridQuery

    );
    @GET("biis_set_user_appid")
    Call<status> status(
            @Query("userid") String arousedQuery,
            @Query("appid") String appidQuery
    );
    @GET("GetAppUserProfile")
    Call<userinfoProfeller> userinforrof( @Query("userid") String arousedQuery);

    /*@POST("SellerSetResponse")
    Call<List<Messagesbiis>> postmsg0(
            //@Query("id") String idQuery,
            //@Query("dateTimeOffset") String dateTimeOffsetQuery,
            //@Query("dateTimeUTC") Integer dateTimeUTCQuery,
            @Query("content") String contentQuery,
            @Query("cost") Float costQuery,
            @Query("userRequestId") String userRequestIdQuery,
            //@Query("userRequest") String userRequestQuery,
            @Query("appUserId") String appUserIdQuery
            //@Query("appUser") String appUserQuery
    );*/

    /*@POST("AddChatMessage")
    Call<List<Messagesbiis>> postmsg1(
            //@Query("id") String idQuery,
            @Query("parentId") String parentIdQuery,//
            //@Query("dateTimeOffset") String dateTimeOffsetQuery,
            //@Query("dateTimeUTC") Integer dateTimeUTCQuery,
            @Query("content") String contentQuery,//
            //@Query("attachedFilePath") String attachedFilePathQuery,
            @Query("appUserId") String appUserIdQuery,//
            //@Query("appUser") String appUserQuery,
            @Query("userRequestId") String userRequestIdQuery//
            //@Query("userRequest") String userRequestQuery
    );*/


    @POST("AddChatMessage")
    Call<postAddChatMessage> postAddChatMessage(
            @Body postAddChatMessage postaddchatmessage);


    @POST("SellerSetResponse")
    Call<postSellerSetResponse> postSellerSetResponse(
            @Body postSellerSetResponse postsellersetresponse);
}
