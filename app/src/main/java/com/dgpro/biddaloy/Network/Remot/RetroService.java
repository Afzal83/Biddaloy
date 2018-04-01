package com.dgpro.biddaloy.Network.Remot;

import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.Network.Model.AboutUsModel;
import com.dgpro.biddaloy.Network.Model.AttendenceModel;
import com.dgpro.biddaloy.Network.Model.BlogCatagoryList;
import com.dgpro.biddaloy.Network.Model.BlogListModel;
import com.dgpro.biddaloy.Network.Model.DiaryModel;
import com.dgpro.biddaloy.Network.Model.DueModel;
import com.dgpro.biddaloy.Network.Model.FcmSubmitResponseMedel;
import com.dgpro.biddaloy.Network.Model.InboxModel;
import com.dgpro.biddaloy.Network.Model.LibraryListModel;
import com.dgpro.biddaloy.Network.Model.LoginDataModel;
import com.dgpro.biddaloy.Network.Model.MailDetailsModel;
import com.dgpro.biddaloy.Network.Model.NoticeModel;
import com.dgpro.biddaloy.Network.Model.OutboxModel;
import com.dgpro.biddaloy.Network.Model.PasswordModel;
import com.dgpro.biddaloy.Network.Model.PaymentListModel;
import com.dgpro.biddaloy.Network.Model.PaymentSubmitResponseModel;
import com.dgpro.biddaloy.Network.Model.PaymentSystemListModel;
import com.dgpro.biddaloy.Network.Model.ResultListModel;
import com.dgpro.biddaloy.Network.Model.RoutineListModel;
import com.dgpro.biddaloy.Network.Model.SearchModel;
import com.dgpro.biddaloy.Network.Model.SentMailResponseModel;
import com.dgpro.biddaloy.Network.Model.StudentListModel;
import com.dgpro.biddaloy.Network.Model.SubmitBlogResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Babu on 1/5/2018.
 */

public interface RetroService {

    @POST("api/login.php")
    @FormUrlEncoded
    Call<LoginDataModel> getLoginData(@Field("user_name") String user_name,
                                      @Field("password") String password,
                                      @Field("category") String category);

    @POST("api/attendance.php")
    @FormUrlEncoded
    Call<AttendenceModel> getAttendenceData(@Field("user_name") String user_name,
                                            @Field("password") String password,
                                            @Field("category") String category,
                                            @Field("month") String month,
                                            @Field("year") String year,
                                            @Field("student_id") String student_id,
                                            @Field("limit") String limit);

    @POST("api/notice.php")
    @FormUrlEncoded
    Call<NoticeModel> getNotice(@Field("user_name") String user_name,
                                @Field("password") String password,
                                @Field("category") String category,
                                @Field("limit") String limit);

    @POST("api/diary.php")
    @FormUrlEncoded
    Call<DiaryModel> getDiary(@Field("user_name") String user_name,
                              @Field("password") String password,
                              @Field("category") String category,
                              @Field("month") String month,
                              @Field("year") String year,
                              @Field("student_id") String student_id);

    @POST("api/paymentlist.php")
    @FormUrlEncoded
    Call<PaymentListModel> getPaymentList(@Field("user_name") String user_name,
                                          @Field("password") String password,
                                          @Field("category") String category,
                                          @Field("student_id") String student_id);

    @POST("api/search.php")
    @FormUrlEncoded
    Call<SearchModel> getSearchResult(@Field("search") String search,
                                      @Field("whom") String whom);

    @POST("api/inbox.php")
    @FormUrlEncoded
    Call<InboxModel> getInboxMail(@Field("user_name") String search,
                                  @Field("password") String password,
                                  @Field("category") String category,
                                  @Field("limit") String limit);

    @POST("api/outbox.php")
    @FormUrlEncoded
    Call<OutboxModel> getOutboxMail(@Field("user_name") String search,
                                    @Field("password") String password,
                                    @Field("category") String category,
                                    @Field("limit") String limit);

    @POST("api/child_list.php")
    @FormUrlEncoded
    Call<StudentListModel> getMyStudentList(@Field("user_name") String user_name,
                                            @Field("password") String password);

    @POST("api/result.php")
    @FormUrlEncoded
    Call<ResultListModel> getResultList(@Field("user_name") String user_name,
                                        @Field("password") String password,
                                        @Field("category") String category,
                                        @Field("student_id") String student_id);
    @POST("api/routine.php")
    @FormUrlEncoded
    Call<RoutineListModel> getStudentRoutine(@Field("user_name") String user_name,
                                             @Field("password") String password,
                                             @Field("category") String category,
                                             @Field("student_id") String student_id);

    @POST("api/due.php")
    @FormUrlEncoded
    Call<DueModel> getDue(@Field("user_name") String user_name,
                          @Field("password") String password,
                          @Field("category") String category,
                          @Field("student_id") String student_id);



    @POST("api/mail_details.php")
    @FormUrlEncoded
    Call<MailDetailsModel> getDetailsMail(@Field("user_name") String user_name,
                                          @Field("password") String password,
                                          @Field("category") String category,
                                          @Field("mail_id") String mail_id);

    @Multipart
    @POST("api/mail.php")
    Call<SentMailResponseModel> sendMail (@Part("user_name") RequestBody user_name,
                                          @Part("password") RequestBody password,
                                          @Part("category") RequestBody category,
                                          @Part("receiver_id") RequestBody receiver_id,
                                          @Part("receiver_category") RequestBody receiver_category,
                                          @Part("subject") RequestBody subject,
                                          @Part("message") RequestBody message);

    @POST("api/blog_list.php")
    @FormUrlEncoded
    Call<BlogListModel> getBlog (@Field("user_name") String user_name,
                                 @Field("password") String password,
                                 @Field("category") String category);
    @POST("api/blog_category.php")
    @FormUrlEncoded
    Call<BlogCatagoryList> getBlogCatagories (@Field("user_name") String user_name,
                                              @Field("password") String password,
                                              @Field("category") String category);

    @Multipart
    @POST("api/blog_input.php")
    Call<SubmitBlogResponseModel> submitBlog(@Part("user_name") RequestBody user_name,
                                             @Part("password") RequestBody password,
                                             @Part("category") RequestBody category,
                                             @Part("blog_category") RequestBody blog_category,
                                             @Part("blog_title") RequestBody blog_title,
                                             @Part("blog_details") RequestBody blog_details,
                                             @Part MultipartBody.Part image);

    @POST("api/onlineinfo.php")
    @FormUrlEncoded
    Call<PaymentSystemListModel> getPaymentSystems (@Field("user_name") String user_name,
                                                    @Field("password") String password,
                                                    @Field("category") String category);

    @POST("api/payment.php")
    @FormUrlEncoded
    Call<PaymentSubmitResponseModel> submitPayment (@Field("user_name") String user_name,
                                                    @Field("password") String password,
                                                    @Field("category") String category,
                                                    @Field("student_id") String student_id,
                                                    @Field("amount") String amount,
                                                    @Field("payment_system") String payment_system,
                                                    @Field("account_number") String account_number,
                                                    @Field("transection_id") String transection_id,
                                                    @Field("comment") String comment);


    @POST("api/library.php")
    @FormUrlEncoded
    Call<LibraryListModel> downLoadLibrary(@Field("user_name") String user_name,
                                           @Field("password") String password,
                                           @Field("category") String category,
                                           @Field("limit") String limit);


    @POST("api/search.php")
    @FormUrlEncoded
    Call<SearchModel> searchUser(@Field("search") String search,
                                            @Field("whom") String whom);

    @POST("api/institute.php")
    @FormUrlEncoded
    Call<AboutInstituteModel> aboutInstitute(@Field("user_name") String user_name,
                                             @Field("password") String password,
                                             @Field("category") String category);


    @POST("api/push_notification.php")
    @FormUrlEncoded
    Call<FcmSubmitResponseMedel> sendPushNotification(@Field("user_name") String user_name,
                                                      @Field("password") String password,
                                                      @Field("category") String category,
                                                      @Field("token") String token,
                                                      @Field("device_id") String device_id);




    @Multipart
    @POST("api/profile_update.php")
    Call<SubmitBlogResponseModel> uploadProfileImage(@Part("user_name") RequestBody user_name,
                                                     @Part("password") RequestBody password,
                                                     @Part("category") RequestBody category,
                                                     @Part MultipartBody.Part image);

    @POST("api/password.php")
    @FormUrlEncoded
    Call<PasswordModel> changePassword(@Field("user_name") String user_name,
                                       @Field("password") String password,
                                       @Field("category") String category,
                                       @Field("old_password") String old_password,
                                       @Field("new_password") String new_password,
                                       @Field("again_password") String again_password);
}
