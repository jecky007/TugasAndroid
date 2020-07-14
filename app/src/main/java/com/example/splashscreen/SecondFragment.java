package com.example.splashscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.splashscreen.apihelper.AppService;
import com.example.splashscreen.apihelper.BookApiService;
import com.example.splashscreen.model.Book;
import com.example.splashscreen.model.LoginResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SecondFragment extends Fragment {
    Retrofit retrofit;
    EditText judul, penulis, penerbit, tahun, harga;
    Button button3, button4;
    ImageView ImageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 1;//kode response untuk operasi pilih image
    private String base64Image = ""; // atribut global untuk menampung hasil base64
    private View view;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_second, container, false);

        judul = view.findViewById(R.id.judul);
        penulis = view.findViewById(R.id.penulis);
        penerbit = view.findViewById(R.id.penerbit);
        tahun = view.findViewById(R.id.tahun);
        harga = view.findViewById(R.id.harga);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        ImageView = view.findViewById(R.id.image);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Add gambar", Toast.LENGTH_SHORT).show();
                imageChooser();
            }
        });

        return view;
    }


    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        InputStream imageStream;
        String encodeImage = "";
        ImageView.setImageURI(uri);

        try {
            imageStream = getContext().getContentResolver().openInputStream(uri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            encodeImage = encodeImage(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        base64Image = encodeImage;

    }


    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga) {
        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(Integer.valueOf(tahun));
        book.setThumb(base64Image);

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<LoginResult> result = apiService.insertNewBook(AppService.getToken(), book);
        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.body().isSuccess()) {
                    Log.e("TAG", "berhasil menambah buku");
                } else {
                    Log.e("TAG", "gagal menambah buku");
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                t.printStackTrace();

            }
        });



        }

}