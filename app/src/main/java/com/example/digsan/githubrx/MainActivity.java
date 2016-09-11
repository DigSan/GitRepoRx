package com.example.digsan.githubrx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digsan.githubrx.list.RecyclerViewAdapter;
import com.example.digsan.githubrx.list.RepositoryViewModel;
import com.example.digsan.githubrx.models.Item;
import com.example.digsan.githubrx.models.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private EditText searchEdit;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdit = (EditText) findViewById(R.id.searchEditText);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_conteiner);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter(List<RepositoryViewModel> searchData){
        searchEdit.clearFocus();

        mAdapter = new RecyclerViewAdapter(searchData);
        mAdapter.getPositionClicks()
                .subscribe(elem-> {
                    startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtra("user", elem.author));
                });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void search(View v){
        try {
            if (searchEdit.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Строка поиска не может быть пустой.", Toast.LENGTH_SHORT).show();
                return;
            }

            String searchString = searchEdit.getText().toString();

            Observable<SearchResponse> repositories = Service.getInstance().gitHubService.getSearchData(searchString, 20);
            repositories.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(resp-> resp.getItems())
                    .map(repository-> {
                        List<RepositoryViewModel> repositoryViewModels = new ArrayList<RepositoryViewModel>();
                        for (Item item : repository){
                            repositoryViewModels.add(new RepositoryViewModel(
                                    item.getOwner().getAvatarUrl(),
                                    item.getOwner().getLogin(),
                                    item.getDescription(),
                                    item.getName()));
                        }
                        return repositoryViewModels;
                    })
                    .subscribe(repos -> {
                        setAdapter(repos);
                    });
        }
        catch (Exception e){
            Log.e("11111", e.getMessage().toString());
        }

    }
}
