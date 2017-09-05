package com.yinhaojun.pulltorefreshview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhaojun.library.PullToRefreshBase;
import com.yinhaojun.library.PullToRefreshHorizontalRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PullToRefreshRecyclerHorizontalActivity extends AppCompatActivity {

    private PullToRefreshHorizontalRecyclerView pullToRefreshHorizontalRecyclerView;
    private List<String> datas = new ArrayList<>();
    String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_to_refresh_header_horizontal);

        pullToRefreshHorizontalRecyclerView = (PullToRefreshHorizontalRecyclerView) findViewById(R.id.pull_refresh_recyclerview);
        pullToRefreshHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        pullToRefreshHorizontalRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);

        TestAdapter adapter = new TestAdapter();
        pullToRefreshHorizontalRecyclerView.setAdapter(adapter);
        pullToRefreshHorizontalRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new PullToRefreshRecyclerHorizontalActivity.GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new PullToRefreshRecyclerHorizontalActivity.GetDataTask().execute();
            }
        });

    }


    private class TestAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            TextView textView = new TextView(PullToRefreshRecyclerHorizontalActivity.this);
            textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
            pullToRefreshHorizontalRecyclerView.getAdapter().notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            pullToRefreshHorizontalRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
