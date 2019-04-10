package com.android.lily.business;

import com.android.lily.model.Logistics;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author rape flower
 * @Date 2018-04-19 13:49
 * @Describe
 */
public interface ApiService {

    /**
     * 获取用户数据
     * @param user
     * @return
     */
    @GET("users/{user}/repos")
    Call<ResponseBody> listRepos(@Path("user") String user);

    /**
     * 查询快递数据
     * <p>返回原始数据</p>
     * @param type
     * @param postid
     * @return
     */
    @GET("query")
    Call<ResponseBody> query(@Query("type") String type, @Query("postid") String postid);

    /**
     * 查询快递数据
     * @param type
     * @param postid
     * @return
     */
    @GET("query")
    Call<Logistics> getLogistics(@Query("type") String type, @Query("postid") String postid);

    /**
     * 查询快递数据
     * @param type
     * @param postid
     * @return
     */
    @GET("query")
    Observable<Logistics> getLogisticsByRx(@Query("type") String type, @Query("postid") String postid);

    /**
     * 查询快递数据
     * @param type
     * @param postid
     * @return
     */
    @FormUrlEncoded
    @POST("query")
    Observable<Logistics> hqLogisticsBy(@Field("type") String type, @Field("postid") String postid);
}
