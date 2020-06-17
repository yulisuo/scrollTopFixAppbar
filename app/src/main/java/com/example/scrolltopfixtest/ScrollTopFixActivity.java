package com.example.scrolltopfixtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;

public class ScrollTopFixActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = "scroll_top_fix";
    RecyclerView rv;
    ViewGroup content;
    AppBarLayout appBarLayout;
    AppCompatTextView title, subTitle;
    float minHeight, transY, transY2;
    int maxOffset;
    int count = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_top_fix);

        initViews();
    }

    private void initViews() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter());
        minHeight = getResources().getDimension(R.dimen.content_min_height);
        transY = getResources().getDimension(R.dimen.trans_y);
        transY2 = getResources().getDimension(R.dimen.trans_y2);

        appBarLayout = findViewById(R.id.app_bar);
        content = findViewById(R.id.app_bar_content);
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subtitle);

        appBarLayout.addOnOffsetChangedListener(this);
    }

    // offset 0 --> -315(minHeight - height)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (maxOffset == 0) {
            maxOffset = (int) (minHeight - appBarLayout.getHeight());
        }
        float progress = 1f - Math.abs(verticalOffset * 1f / maxOffset);
        log("progress:%s", progress);
        float scale = 1f + 0.5f * progress;

        title.setTranslationY(progress * transY);
        title.setScaleX(scale);
        title.setScaleY(scale);

        subTitle.setTranslationY(progress * transY2);
        subTitle.setScaleX(scale);
        subTitle.setScaleY(scale);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(ScrollTopFixActivity.this).inflate(R.layout.selected_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ((AppCompatTextView) holder.itemView.findViewById(R.id.text)).setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private void log(String s, Object... args) {
        Log.i(TAG, String.format(s, args));
    }
}