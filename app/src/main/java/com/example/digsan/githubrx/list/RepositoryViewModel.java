package com.example.digsan.githubrx.list;

/**
 * Created by digsan on 10.09.2016.
 */
public class RepositoryViewModel {
    public String avatarUrl;
    public String author;
    public String description;
    public String repName;

    public RepositoryViewModel(String avatarUrl, String author, String description, String repName){
        this.author = author;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.repName = repName;
    }

}
