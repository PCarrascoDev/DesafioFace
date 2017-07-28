package com.desafiolatam.desafioface.views.main;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.adapters.DevelopersAdapter;
import com.desafiolatam.desafioface.network.GetUsers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersFragment extends Fragment {

    private SwipeRefreshLayout reload;
    private DevelopersAdapter adapter;
    private boolean pendingRequest = false;

    public DevelopersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reload = (SwipeRefreshLayout) view.findViewById(R.id.reloadSrl);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.developersRv);

        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        linearLayoutManager.findLastCompletelyVisibleItemPosition();
        adapter = new DevelopersAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findLastVisibleItemPosition();
                int total = linearLayoutManager.getItemCount();
                if (total - position < 10)
                {
                    if (!pendingRequest)
                    {
                        Map<String, String> queryParams = new HashMap<>();
                        queryParams.put("page", String.valueOf(total/10));

                        new ScrollRequest(3).execute(queryParams);

                    }
                }
            }
        });

        reload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.update();
                        pendingRequest = false;
                        reload.setRefreshing(false);
                    }
                }, 888);
            }
        });
    }

    public void updateAdapter(String name) {
        pendingRequest = false;
        adapter.find(name);
    }

    private class ScrollRequest extends GetUsers {

        public ScrollRequest(int additionalPages) {
            super(additionalPages);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pendingRequest = true;
            reload.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            pendingRequest = false;
            reload.setRefreshing(false);
        }
    }
}
