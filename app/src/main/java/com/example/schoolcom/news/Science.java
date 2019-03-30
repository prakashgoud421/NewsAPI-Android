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

import com.example.schoolcom.NewsDetailActivity;
import com.example.schoolcom.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.example.schoolcom.Models.news.DataClass;
import com.example.schoolcom.Models.news.HandleXml;
import com.example.schoolcom.Models.news.Netutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created By prakash
 * A simple {@link Fragment} subclass.
 */
public class Science extends Fragment {


    public static Activity activity;

    public static Context context;
    ShimmerFrameLayout mShimmerViewContainer;

    private HandleXml obj;
    ProgressDialog progress;
    public static List<DataClass> scienceArray;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar gifLoading;
    public Science() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.view_news_recycler, container, false);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        mShimmerViewContainer=v.findViewById(R.id.shimmer_news);
        mShimmerViewContainer.setAlpha(0.2f);
        mShimmerViewContainer.setDuration(1500);
        mShimmerViewContainer.setBaseAlpha(0.5f);
        swipeRefreshLayout=v.findViewById(R.id.swipe_news);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color._red));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadNews();

        mShimmerViewContainer.startShimmerAnimation();
          gifLoading = v.findViewById(R.id.gifLoading);
        if (isNetworkAvailable())
        {

        } else {
//            gifLoading.setVisibility(View.GONE);
//            mShimmerViewContainer.stopShimmerAnimation();
            Toast.makeText(getActivity(), "poor Internet", Toast.LENGTH_LONG).show();
        }

        return v;
    }

    private void loadNews() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral1(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<DataClass> cricketDataStorages1;
                cricketDataStorages1 = Netutils.extractCricketData(response);

                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral2(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DataClass> cricketDataStorages2;
                        cricketDataStorages2 = Netutils.extractCricketData(response);

                        cricketDataStorages1.addAll(cricketDataStorages2);

                        RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral7(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<DataClass> cricketDataStorages3;
                                cricketDataStorages3 = Netutils.extractCricketData(response);
                                cricketDataStorages1.addAll(cricketDataStorages3);
                                RequestQueue requestQueue3 = Volley.newRequestQueue(context);
                                StringRequest stringRequest3 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral4(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ArrayList<DataClass> cricketDataStorages4;
                                        cricketDataStorages4 = Netutils.extractCricketData(response);
                                        cricketDataStorages1.addAll(cricketDataStorages4);
                                        RequestQueue requestQueue4 = Volley.newRequestQueue(context);
                                        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral5(), new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                ArrayList<DataClass> cricketDataStorages5;
                                                cricketDataStorages5 = Netutils.extractCricketData(response);
                                                cricketDataStorages1.addAll(cricketDataStorages5);
                                                RequestQueue requestQueue5 = Volley.newRequestQueue(context);
                                                StringRequest stringRequest5 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlGeneral6(), new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        ArrayList<DataClass> cricketDataStorages6;
                                                        cricketDataStorages6 = Netutils.extractCricketData(response);

                                                        cricketDataStorages1.addAll(cricketDataStorages6);

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        progress.dismiss();
                                                    }
                                                });
                                                requestQueue5.add(stringRequest5);

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
                        gifLoading.setVisibility(View.GONE);
                    }
                });

                requestQueue1.add(stringRequest1);
                gifLoading.setVisibility(View.GONE);
                Collections.shuffle(cricketDataStorages1);
                adapter = new ContentAdapter(recyclerView.getContext(), cricketDataStorages1);
                recyclerView.setAdapter(adapter);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                //adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Log.v("2nd", "science3");
        requestQueue.add(stringRequest);
        //progress.dismiss();

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
                    intent.putExtra("DES",scienceArray.get(pos).getDescription());
                    intent.putExtra("TITLE",scienceArray.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",scienceArray.get(pos).getUrltoImage());
                    intent.putExtra("UPDATED",scienceArray.get(pos).getUpdates());
                    intent.putExtra("LINK",scienceArray.get(pos).getUrl());

                    try {
                        intent.putExtra("DES1", scienceArray.get(pos + 1).getDescription());
                        intent.putExtra("TITLE1", scienceArray.get(pos + 1).getTitle());
                        intent.putExtra("IMAGEURL1", scienceArray.get(pos + 1).getUrltoImage());
                        intent.putExtra("UPDATED1", scienceArray.get(pos + 1).getUpdates());
                        intent.putExtra("LINK1", scienceArray.get(pos + 1).getUrl());

                        intent.putExtra("DES2", scienceArray.get(pos + 2).getDescription());
                        intent.putExtra("TITLE2", scienceArray.get(pos + 2).getTitle());
                        intent.putExtra("IMAGEURL2", scienceArray.get(pos + 2).getUrltoImage());
                        intent.putExtra("UPDATED2", scienceArray.get(pos + 2).getUpdates());
                        intent.putExtra("LINK2", scienceArray.get(pos + 2).getUrl());

                        //header Title
                        String text = "General News";
                        intent.putExtra("text", text);
                    }
                    catch (IndexOutOfBoundsException en){
                        intent.putExtra("DES1", scienceArray.get(pos - 1).getDescription());
                        intent.putExtra("TITLE1", scienceArray.get(pos - 1).getTitle());
                        intent.putExtra("IMAGEURL1", scienceArray.get(pos - 1).getUrltoImage());
                        intent.putExtra("UPDATED1", scienceArray.get(pos - 1).getUpdates());
                        intent.putExtra("LINK1", scienceArray.get(pos - 1).getUrl());

                        intent.putExtra("DES2", scienceArray.get(pos - 2).getDescription());
                        intent.putExtra("TITLE2", scienceArray.get(pos - 2).getTitle());
                        intent.putExtra("IMAGEURL2", scienceArray.get(pos - 2).getUrltoImage());
                        intent.putExtra("UPDATED2", scienceArray.get(pos - 2).getUpdates());
                        intent.putExtra("LINK2", scienceArray.get(pos - 2).getUrl());

                        //header Title
                        String text = "General News";
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

        List<DataClass> scienceData;
        List<DataClass> originalList;
        public ContentAdapter(Context context, ArrayList<DataClass> scienceData) {

            this.scienceData=scienceData;
            scienceArray=scienceData;
            originalList=scienceData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            Glide.with(context).load(scienceData.get(position).getUrltoImage()).override(500, 200)
                    .fitCenter().into(holder.picture);
            if(scienceData.get(position).getTitle()!=null){
                holder.name.setText(scienceData.get(position).getTitle());

            }
            if(scienceData.get(position).getDescription()!=null){
                holder.description.setText(scienceData.get(position).getDescription());


            }
            if(scienceData.get(position).getUpdates()!=null){
                String updated=scienceData.get(position).getUpdates();
                String[] publish=updated.split("T");
                if(!publish[0].equalsIgnoreCase("null")){
                    holder.date.setText(publish[0]);
                }else{
                    holder.date.setText("N/A");
                    Log.e("date null Value",publish[0]);
                }
            }

            //Picasso.with(context).load(scienceData.get(position).getUrltoImage()).into(holder.picture);

        }

        @Override
        public int getItemCount() {
            if(scienceData==null)
                return 0;
            else
                return scienceData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    scienceData = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            scienceArray=originalList;

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
            scienceArray=results;
            return results;
        }




    }


    public static void SearchPlayer(String text)
    {
        Log.v("2nd",text+"2");
        adapter.getFilter().filter(text);

    }
}
