package com.example.dmitry.a1c_client.data.network.retrofit;

import com.example.dmitry.a1c_client.data.network.http.OkHttpMoon;
import com.example.dmitry.a1c_client.data.network.model.EquipmentOrder;
import com.example.dmitry.a1c_client.data.network.model.IncomeOrder;
import com.example.dmitry.a1c_client.data.network.model.Metadata;
import com.example.dmitry.a1c_client.data.network.model.Odata;
import com.example.dmitry.a1c_client.data.network.model.ShipmentOrder;
import com.example.dmitry.a1c_client.data.network.model.Store;
import com.example.dmitry.a1c_client.data.network.model.User;
import com.example.dmitry.a1c_client.domain.entity.Unit;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by Admin on 14.12.2016.
 */

public class MoonServiceGenerator {
    public static final String API_BASE_URL = "http://moon/Test/odata/standard.odata/";
    private static final OkHttpClient.Builder httpClientBuilder =
            OkHttpMoon.getClientBuilder(OkHttpMoon.administrator, OkHttpMoon.password);
    private static final String FORMAT = "?$format=application/json";


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory
                            .create());

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpBuilder = httpClientBuilder;

        Retrofit retrofit = builder.client(httpBuilder.build()).build();
        return retrofit.create(serviceClass);
    }

    public interface MoonApi {
        @GET(FORMAT)
        Observable<Odata<Metadata>> metadata();

        @GET("{url}" + FORMAT)
        Observable<ResponseBody> data(@Path("url") String url);

        @GET("Document_ПриходныйОрдер" + FORMAT)
        Call<Odata<IncomeOrder>> incomeOrders();

        @GET("Document_РасходныйОрдер" + FORMAT)
        Call<Odata<ShipmentOrder>> shipmentOrders();

        @GET("Document_Комплектация" + FORMAT)
        Call<Odata<EquipmentOrder>> equipmentOrders();

        @GET("Catalog_Пользователи(guid'{guid}')" + FORMAT)
        Call<User> user(@Path("guid") String userKey);

        @GET("Catalog_Пользователи)" + FORMAT)
        Call<Odata<User>> users();

        @GET("Catalog_Склады)" + FORMAT)
        Call<Odata<Store>> stores();
        /*
        @GET("Catalog_Контрагенты"+FORMAT_JSON)
        Observable<OdataMetadata<Client>> clients();

        @GET("Catalog_Контрагенты(guid'{guid}')"+FORMAT_JSON)
        Call<ResponseBody> client(@Path("guid") String key);

        @GET("Catalog_Контрагенты" + FORMAT_JSON)
        Observable<OdataMetadata<Client>> clientsFiltered(@Query("$filter") String oDataFilter);

        @PATCH("Catalog_Контрагенты(guid'{guid}')"+FORMAT_JSON)
        Call<ResponseBody> updateClient(@Path("guid") String key, @Body Client client);
        //?$filter=substringof('ПРД', Артикул)
        //&$filter=substringof('Чеб',Description)
        @POST("Catalog_Контрагенты"+FORMAT_JSON)
        Call<ResponseBody> newClient(@Body Client client);

        @POST("Document_Док(guid'{guid}')/Post()" + FORMAT_JSON)
        Call<ResponseBody> post(@Path("guid") String key);

        @GET("Document_Док" + FORMAT_JSON)
        Observable<OdataMetadata<Document>> documents();

        @GET("Document_Док(guid'{guid}')" + FORMAT_JSON)
        Call<Document> document(@Path("guid") String key);

        @PATCH("Document_Док(guid'{guid}')"+FORMAT_JSON)
        Call<ResponseBody> updateDocument(@Path("guid") String key, @Body Document client);
        */
    }
}
