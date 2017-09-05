package com.yinhaojun.pulltorefreshview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhaojun.library.PullToRefreshBase;
import com.yinhaojun.library.PullToRefreshHorizontalRecyclerView;
import com.yinhaojun.library.PullToRefreshVerticalRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PullToRefreshRecyclerVerticalActivity extends AppCompatActivity {

    private PullToRefreshVerticalRecyclerView pullToRefreshVerticalRecyclerView;
    private List<String> datas = new ArrayList<>();
    String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_recycler_vertical);

        pullToRefreshVerticalRecyclerView = (PullToRefreshVerticalRecyclerView) findViewById(R.id.pull_refresh_recyclerview);
        pullToRefreshVerticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pullToRefreshVerticalRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);

        TestAdapter adapter = new TestAdapter();
        pullToRefreshVerticalRecyclerView.setAdapter(adapter);
        pullToRefreshVerticalRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new PullToRefreshRecyclerVerticalActivity.GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new PullToRefreshRecyclerVerticalActivity.GetDataTask().execute();
            }
        });

    }


    private class TestAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            TextView textView = new TextView(PullToRefreshRecyclerVerticalActivity.this);
            textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof TextView) {
                this.textView = (TextView) itemView;
            }
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            Collections.addAll(datas, mStrings);
            pullToRefreshVerticalRecyclerView.getAdapter().notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            pullToRefreshVerticalRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
