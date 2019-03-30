package com.example.schoolcom.news;

//created by prakash

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.example.schoolcom.NewsDetailActivity;
import com.example.schoolcom.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.example.schoolcom.Models.news.DataClass;
import com.example.schoolcom.Models.news.HandleXml;
import com.example.schoolcom.Models.news.Netutils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Music extends Fragment {


    public static Activity activity;
    public static Context context;
    ProgressDialog progress;
    public static List<DataClass> musicArray;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;

    public Music() {

    }

    @Override
    public void onResume() {
        super.onResume();
//        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
//        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();
//        progress = ProgressDialog.show(getActivity(), "",
//                "loading....", true);
//        progress.setCancelable(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(MainNewsFragment.flag&&progress!=null&&progress.isShowing())
//            progress.dismiss();
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.view_news_recycler, container, false);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        mShimmerViewContainer=v.findViewById(R.id.shimmer_news);
        mShimmerViewContainer.setAlpha(0.2f);
        mShimmerViewContainer.setBaseAlpha(0.5f);
        mShimmerViewContainer.setDuration(1500);
        mShimmerViewContainer.startShimmerAnimation();
        swipeRefreshLayout=v.findViewById(R.id.swipe_news);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color._red));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        final ProgressBar gifLoading = v.findViewById(R.id.gifLoading);
        if (isNetworkAvailable())
        {

        } else {
//            gifLoading.setVisibility(View.GONE);
//            mShimmerViewContainer.stopShimmerAnimation();
            Toast.makeText(getActivity(), "poor Internet", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
        loadNews();

        return v;
    }

    private void loadNews() {
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrlTOP(),new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<DataClass> cricketDataStorages1;
                cricketDataStorages1 = Netutils.extractCricketData(response);

                RequestQueue requestQueue1=Volley.newRequestQueue(context);
                StringRequest stringRequest1=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DataClass> cricketDataStorages2;
                        cricketDataStorages2 = Netutils.extractCricketData(response);
                        cricketDataStorages1.addAll(cricketDataStorages2);
                        RequestQueue requestQueue2=Volley.newRequestQueue(context);
                        StringRequest stringRequest2=new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlMusicTop1(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                ArrayList<DataClass> cricketDataStorages3;
                                cricketDataStorages3 = Netutils.extractCricketData(response);
                                cricketDataStorages1.addAll(cricketDataStorages3);
                                RequestQueue requestQueue3=Volley.newRequestQueue(context);
                                StringRequest stringRequest3=new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlMusicTop2(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ArrayList<DataClass> cricketDataStorages4;
                                        cricketDataStorages4 = Netutils.extractCricketData(response);
                                        cricketDataStorages1.addAll(cricketDataStorages4);
                                        RequestQueue requestQueue4=Volley.newRequestQueue(context);
                                        StringRequest stringRequest4=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrlTOP3(), new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                ArrayList<DataClass> cricketDataStorages5;
                                                cricketDataStorages5 = Netutils.extractCricketData(response);
                                                cricketDataStorages1.addAll(cricketDataStorages5);

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        requestQueue4.add(stringRequest4);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requestQueue3.add(stringRequest3);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue2.add(stringRequest2);




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                requestQueue1.add(stringRequest1);
                Collections.shuffle(cricketDataStorages1,new Random(System.nanoTime()));
                adapter = new ContentAdapter(recyclerView.getContext(),cricketDataStorages1);
//                gifLoading.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mShimmerViewContainer.stopShimmerAnimation();

            }
        });


        requestQueue.add(stringRequest);
//        progress.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description,date;
        Button action;
        ImageButton share_button, favourite_button;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_news_business, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_desc);
            action = (Button) itemView.findViewById(R.id.action_button);
            date=(TextView)itemView.findViewById(R.id.card_date);

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int pos = getAdapterPosition();

                    Intent intent=new Intent(context, NewsDetailActivity.class);
                   intent.putExtra("DES",musicArray.get(pos).getDescription());
                    intent.putExtra("TITLE",musicArray.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",musicArray.get(pos).getUrltoImage());
                    intent.putExtra("UPDATED",musicArray.get(pos).getUpdates());
                    intent.putExtra("LINK",musicArray.get(pos).getUrl());

try {


                    intent.putExtra("DES1",musicArray.get(pos +1).getDescription());
                    intent.putExtra("TITLE1",musicArray.get(pos+1).getTitle());
                    intent.putExtra("IMAGEURL1",musicArray.get(pos+1).getUrltoImage());
                    intent.putExtra("UPDATED1",musicArray.get(pos+1).getUpdates());
                    intent.putExtra("LINK1",musicArray.get(pos+1).getUrl());

                    intent.putExtra("DES2", musicArray.get(pos + 2).getDescription());
                    intent.putExtra("TITLE2", musicArray.get(pos + 2).getTitle());
                    intent.putExtra("IMAGEURL2", musicArray.get(pos + 2).getUrltoImage());
                    intent.putExtra("UPDATED2", musicArray.get(pos + 2).getUpdates());
                    intent.putExtra("LINK2", musicArray.get(pos + 2).getUrl());
    //header Title
    String text = "Entertainment News";
    intent.putExtra("text", text);


}
catch (IndexOutOfBoundsException en){
    intent.putExtra("DES1",musicArray.get(pos -1).getDescription());
    intent.putExtra("TITLE1",musicArray.get(pos-1).getTitle());
    intent.putExtra("IMAGEURL1",musicArray.get(pos-1).getUrltoImage());
    intent.putExtra("UPDATED1",musicArray.get(pos-1).getUpdates());
    intent.putExtra("LINK1",musicArray.get(pos-1).getUrl());

    intent.putExtra("DES2", musicArray.get(pos - 2).getDescription());
    intent.putExtra("TITLE2", musicArray.get(pos - 2).getTitle());
    intent.putExtra("IMAGEURL2", musicArray.get(pos - 2).getUrltoImage());
    intent.putExtra("UPDATED2", musicArray.get(pos - 2).getUpdates());
    intent.putExtra("LINK2", musicArray.get(pos - 2).getUrl());
    //header Title
    String text = "Entertainment News";
    intent.putExtra("text", text);
}
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        List<DataClass> musicData;
        List<DataClass> originalList;
        public ContentAdapter(Context context, ArrayList<DataClass> musicData) {

            this.musicData=musicData;
            musicArray=musicData;
            originalList=musicData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {




            Glide.with(context).load(musicData.get(position).getUrltoImage()).override(500, 200)
                    .fitCenter().into(holder.picture);
            if(musicData.get(position).getTitle()!=null){
                holder.name.setText(musicData.get(position).getTitle());

            }
            if(musicData.get(position).getDescription()!=null){
                holder.description.setText(musicData.get(position).getDescription());

            }
            if(musicData.get(position).getUpdates()!=null){
                String updated=musicData.get(position).getUpdates();
                String[] publish=updated.split("T");

                if(!publish[0].equalsIgnoreCase("null")){
                    holder.date.setText(publish[0]);
                }else{
                    holder.date.setText("N/A");
                    Log.e("date null Value",publish[0]);
                }


            }



        }

        @Override
        public int getItemCount() {
            if(musicData==null)
                return 0;
            else
                return musicData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    musicData = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            musicArray=originalList;

                        }
                    } else {
                        filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                    }

                    FilterResults results = new FilterResults();
                    results.values = filteredResults;

                    return results;
                }
            };
        }

        protected List<DataClass> getFilteredResults(String constraint) {
            List<DataClass> results = new ArrayList<>();

            for (int i=0;i<originalList.size();i++) {
                String s=originalList.get(i).getTitle();
                if (s.toLowerCase().contains(constraint)) {
                    results.add(originalList.get(i));

                }
            }
            musicArray=results;
            return results;
        }




    }


    public static void SearchPlayer(String text)
    {
        Log.v("2nd",text+"2");
        adapter.getFilter().filter(text);

    }

}
