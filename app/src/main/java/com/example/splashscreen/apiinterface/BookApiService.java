package com.example.splashscreen.apiinterface;

import com.example.splashscreen.model.ApiResponse;
import com.example.splashscreen.model.Book;
import com.example.splashscreen.model.BookResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApiService {
//    @Headers({
//            "Accept-Encoding: gzip, deflate", //terima body response server dalam bentuk gzip
//            "Content-Encoding: gzip", // content encoding body dalam bentuk gzip
//    })

   @GET("api/buku")
   Call<List<Book>> getAllBuku(@Header("Authorization") String token);
    @POST("api/buku")
    Call<ApiResponse> insertNewBook(@Header("Authorization") String token, @Body Book body);

    @GET("api/buku/byId/{id}")
    Call<BookResult> getBookById(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("api/buku")
    Call<ApiResponse> deleteBook(@Header("Authorization") String token, @Query("id") int id);

    @PUT("api/buku")
    Call<ApiResponse> updateBook(@Header("Authorozaion") String token, @Body Book body);
    //Call<ApiResponse> updateBook(@Header("Authorization") String token, @Body Book body);

}

