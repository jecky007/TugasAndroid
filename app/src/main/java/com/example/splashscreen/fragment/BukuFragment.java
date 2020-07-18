package com.example.splashscreen.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splashscreen.BukuActivity;
import com.example.splashscreen.R;
import com.example.splashscreen.model.Book;
import com.example.splashscreen.service.AppService;
import com.example.splashscreen.adapter.BookAdapter;
import com.example.splashscreen.adapter.MemberListAdapter;
import com.example.splashscreen.apiinterface.BookApiService;
import com.example.splashscreen.utility.RetrofitUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BukuFragment<view> extends Fragment {

    private View view;
    private Retrofit retrofit;
    private String TAG = "homefragment";
    private RecyclerView listMember;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter memberListAdapter;
    protected Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buku, container, false);
        initRetrofit();
        getAllBookData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        listMember = view.findViewById(R.id.listMember);
        linearLayoutManager = new LinearLayoutManager(context);
        memberListAdapter = new MemberListAdapter(context,this);
        listMember.setLayoutManager(linearLayoutManager);
        listMember.setAdapter(memberListAdapter);
    }

    private void initRetrofit() {
        retrofit = RetrofitUtility.initializeRetrofit();

    }

    private void getAllBookData() {
        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<List<Book>> result = apiService.getAllBuku(AppService.getToken());
        result.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                addData(response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });
    }

    private void addData(List<Book> data) {
        List<BookAdapter> bookAdapterList = new ArrayList<>();
        BookAdapter bookAdapter;
        for (Book books : data) {
            bookAdapter = new BookAdapter();
            bookAdapter.setId(books.getId());
            bookAdapter.setJudul(books.getJudul());
            bookAdapter.setPenulis(books.getPenulis());
            bookAdapter.setThumb(books.getThumb());
            bookAdapterList.add(bookAdapter);
        }
        memberListAdapter.addAll(bookAdapterList);
    }
    public void openFragmentDialog(int id) {
        ((BukuActivity)getActivity()).openDialogFragment();
    }

}
