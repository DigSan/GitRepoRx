package com.example.digsan.githubrx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digsan.githubrx.list.DownloadImageTask;
import com.example.digsan.githubrx.list.RepositoryViewModel;
import com.example.digsan.githubrx.models.Item;
import com.example.digsan.githubrx.models.SearchResponse;
import com.example.digsan.githubrx.models.UserResponse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView createDate;
    private TextView company;
    private TextView biograph;

    private ImageView avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.user_email);
        createDate = (TextView) findViewById(R.id.user_createDate);
        company = (TextView) findViewById(R.id.user_company);
        biograph = (TextView) findViewById(R.id.user_biograph);

        avatar = (ImageView) findViewById(R.id.user_avatar);

        try {
            String userLogin = "";
            if (getIntent().getExtras() != null)
                userLogin = getIntent().getExtras().getString("user");

            if (userLogin.isEmpty())
                return;

            Observable<UserResponse> repositories = Service.getInstance().gitHubService.getUser(userLogin);
            repositories.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(repos -> {
                        //if (repos.getName() != null)
                            name.setText(repos.getName());
                        //if (repos.getName() != null)
                            email.setText(repos.getEmail());
                        //if (repos.getName() != null)
                            createDate.setText(repos.getCreatedAt());
                        //if (repos.getName() != null)
                            company.setText(repos.getCompany());
                        if (repos.getAvatarUrl()!= null)
                            new DownloadImageTask(avatar)
                                    .execute(repos.getAvatarUrl());
                        biograph.setText(repos.getBio());
                    });
        }
        catch (Exception e){
            Log.e("11111", e.getMessage().toString());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
