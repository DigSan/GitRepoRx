package com.example.digsan.githubrx.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digsan.githubrx.R;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by digsan on 10.09.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<RepositoryViewModel> records;
    private final PublishSubject<RepositoryViewModel> onClickSubject = PublishSubject.create();

    public RecyclerViewAdapter(List<RepositoryViewModel> records) {
        this.records = records;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_element, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RepositoryViewModel record = records.get(i);
        viewHolder.setContent(record.repName,record.avatarUrl, record.description, record.author);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(record);
            }
        });
    }

    public Observable<RepositoryViewModel> getPositionClicks(){
        return onClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView repositoryName;
        private TextView description;
        private TextView author;

        private ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            repositoryName = (TextView) itemView.findViewById(R.id.repository_name);
            description = (TextView) itemView.findViewById(R.id.description);
            author = (TextView) itemView.findViewById(R.id.author_name);

            avatar = (ImageView) itemView.findViewById(R.id.avatar_image);
        }

        public void setContent(String repositoryName, String avatarUrl, String description, String author){
            new DownloadImageTask(avatar)
                    .execute(avatarUrl);
            this.repositoryName.setText(repositoryName);
            this.author.setText(author);
            this.description.setText(description);
        }
    }
}

