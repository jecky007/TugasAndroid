package com.example.splashscreen.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.splashscreen.R;
import com.example.splashscreen.model.ApiResponse;
import com.example.splashscreen.model.Book;
import com.example.splashscreen.model.BookResult;
import com.example.splashscreen.service.AppService;
import com.example.splashscreen.apiinterface.BookApiService;
import com.example.splashscreen.utility.RetrofitUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;


public class DialogFragment extends Fragment {

    private Retrofit retrofit;

    EditText inputJudul, inputPenulis, inputPenerbit, inputTahun, inputHarga;
    Button button5, button6;
    ImageView ImageView;
    private String base64Image;
    private View view;
    public static final int PICK_IMAGE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog, container, false);

        getData();
        initView();
        return view;

    }

    private void initView() {
        inputJudul = view.findViewById(R.id.inputJudul);
        inputPenulis = view.findViewById(R.id.inputPenulis);
        inputPenerbit = view.findViewById(R.id.inputPenerbit);
        inputTahun = view.findViewById(R.id.inputTahun);
        inputHarga = view.findViewById(R.id.inputHarga);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        ImageView = view.findViewById(R.id.imageView);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();
                deleteBuku();

            }

        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "save", Toast.LENGTH_SHORT).show();
                sendData(inputJudul.getText().toString(), inputPenulis.getText().toString(), inputPenerbit.getText().toString(), inputTahun.getText().toString(), inputHarga.getText().toString());
            }
        });
    }

    private void getData() {
        retrofit = RetrofitUtility.initializeRetrofit();

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<BookResult> result = apiService.getBookById(AppService.getToken(), AppService.getIdBook());
        result.enqueue(new Callback<BookResult>() {
            @Override
            public void onResponse(Call<BookResult> call, Response<BookResult> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(getContext(),"judul" ,Toast.LENGTH_SHORT).show();

                    String judul = response.body().getRecord().getJudul() != null ? response.body().getRecord().getJudul() : "";
                    String penulis = response.body().getRecord().getPenulis() != null ? response.body().getRecord().getPenulis() : "";
                    String penerbit = response.body().getRecord().getPenerbit() != null ? response.body().getRecord().getPenerbit() : "";
                    String tahun = response.body().getRecord().getTahun() != null ? response.body().getRecord().getTahun() : "";
                    String harga = response.body().getRecord().getHarga() > 0 ? String.valueOf(response.body().getRecord().getHarga()) : "";

                    Log.e(TAG, "judul: " + judul);
                    Log.e(TAG, "penulis: " + penulis);
                    Log.e(TAG, "penerbit: " + penerbit);
                    Log.e(TAG, "tahun: " + tahun);
                    Log.e(TAG, "harga: " + harga);

                    inputJudul.setText(judul);
                    inputPenulis.setText(penulis);
                    inputPenerbit.setText(penerbit);
                    inputTahun.setText(tahun);
                    inputHarga.setText(harga);

                    setImageThumb(response.body().getRecord().getThumb());
                }
            }


            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {

                t.printStackTrace();

            }


        });
    }
    private Bitmap setImageThumb(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView.setImageBitmap(decodedByte);
        return decodedByte;
    }

    private void deleteBuku(){
        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<ApiResponse> result = apiService.deleteBook(AppService.getToken(), AppService.getIdBook());
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(getContext(),"delete berhasil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            ImageView.setImageURI(uri);
            InputStream imageStream;
            String encodedImage = "";

            ImageView.getLayoutParams().height = 400;
            ImageView.getLayoutParams().width = 300;

            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodedImage + encodedImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            base64Image = encodedImage;
        }
    }

    private String encodedImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga) {
        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(tahun);
        book.setThumb(base64Image);

        Log.e(TAG, "sendData: " + book.toString());

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<ApiResponse> result = apiService.updateBook(AppService.getToken(),book);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(getContext(), "berhasil di edit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    }