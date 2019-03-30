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
import com.example.schoolcom.Models.news.DataClass;
import com.example.schoolcom.Models.news.Netutils;
import com.example.schoolcom.NewsDetailActivity;
import com.example.schoolcom.R;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Football extends Fragment {

    ProgressDialog progress;
    public static Activity activity;
    public static Context context;
    public static ContentAdapter adapter;
    public static List<DataClass> footballArray1;
    public static RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;

    public Football()
    {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
        this.context=activity.getApplicationContext();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.view_news_recycler, container, false);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        final ProgressBar gifLoading = v.findViewById(R.id.gifLoading);

        mShimmerViewContainer=v.findViewById(R.id.shimmer_news);
        mShimmerViewContainer.setAlpha(0.2f);
        mShimmerViewContainer.setDuration(1500);
        mShimmerViewContainer.setBaseAlpha(0.5f);

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

        if (isNetworkAvailable())
        {

        } else {
//            gifLoading.setVisibility(View.GONE);
//                mShimmerViewContainer.stopShimmerAnimation();
            Toast.makeText(getActivity(), "poor Internet", Toast.LENGTH_LONG).show();
        }
        //recyclerView.setAdapter(adapter);
        loadNews();

        return v;
    }

    private void loadNews() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, Netutils.getUsgsFootballUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                final ArrayList<DataClass> cricketDataStorages1;
                cricketDataStorages1 = Netutils.extractCricketData(response);


                RequestQueue requestQueue1=Volley.newRequestQueue(context);

                StringRequest stringRequest2=new StringRequest(Request.Method.GET, Netutils.getUsgsFootballTopUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DataClass> cricketDataStorages2;
                        cricketDataStorages2 = Netutils.extractCricketData(response);
                        cricketDataStorages1.addAll(cricketDataStorages2);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue1.add(stringRequest2);
                Collections.shuffle(cricketDataStorages1);
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

            }
        });
        requestQueue.add(stringRequest1);
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
        ImageButton share_button,favourite_button;
        public ViewHolder(final LayoutInflater inflater, final ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_news_business, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_desc);
            action=(Button)itemView.findViewById(R.id.action_button);
            date=(TextView)itemView.findViewById(R.id.card_date);

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context=view.getContext();
                    int pos=getAdapterPosition();

                    Intent intent=new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("DES",footballArray1.get(pos).getDescription());
                    intent.putExtra("TITLE",footballArray1.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",footballArray1.get(pos).getUrltoImage());
                    intent.putExtra("UPDATED",footballArray1.get(pos).getUpdates());
                    intent.putExtra("LINK",footballArray1.get(pos).getUrl());
try {
    intent.putExtra("DES1", footballArray1.get(pos + 1).getDescription());
    intent.putExtra("TITLE1", footballArray1.get(pos + 1).getTitle());
    intent.putExtra("IMAGEURL1", footballArray1.get(pos + 1).getUrltoImage());
    intent.putExtra("UPDATED1", footballArray1.get(pos + 1).getUpdates());
    intent.putExtra("LINK1", footballArray1.get(pos + 1).getUrl());

    intent.putExtra("DES2", footballArray1.get(pos + 2).getDescription());
    intent.putExtra("TITLE2", footballArray1.get(pos + 2).getTitle());
    intent.putExtra("IMAGEURL2", footballArray1.get(pos + 2).getUrltoImage());
    intent.putExtra("UPDATED2", footballArray1.get(pos + 2).getUpdates());
    intent.putExtra("LINK2", footballArray1.get(pos + 2).getUrl());
    //header Title
    String text = "Football News";
    intent.putExtra("text", text);
}
catch (IndexOutOfBoundsException en) {
    intent.putExtra("DES1", footballArray1.get(pos - 1).getDescription());
    intent.putExtra("TITLE1", footballArray1.get(pos - 1).getTitle());
    intent.putExtra("IMAGEURL1", footballArray1.get(pos - 1).getUrltoImage());
    intent.putExtra("UPDATED1", footballArray1.get(pos - 1).getUpdates());
    intent.putExtra("LINK1", footballArray1.get(pos - 1).getUrl());

    intent.putExtra("DES2", footballArray1.get(pos - 2).getDescription());
    intent.putExtra("TITLE2", footballArray1.get(pos - 2).getTitle());
    intent.putExtra("IMAGEURL2", footballArray1.get(pos - 2).getUrltoImage());
    intent.putExtra("UPDATED2", footballArray1.get(pos - 2).getUpdates());
    intent.putExtra("LINK2", footballArray1.get(pos - 2).getUrl());
    //header Title
    String text = "Football News";
    intent.putExtra("text", text);
}
                    context.startActivity(intent);


                }
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
     //   mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        List<DataClass> footballArray;
        List<DataClass> originalList;
        public ContentAdapter(Context context, ArrayList<DataClass> footballArray) {
            this.footballArray=footballArray;
            footballArray1=footballArray;
            originalList=footballArray;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(context).load(footballArray.get(position).getUrltoImage()).override(500, 200)
                    .fitCenter().into(holder.picture);

            if(footballArray.get(position).getTitle()!=null){
                holder.name.setText(footballArray.get(position).getTitle());

            }
            if(footballArray.get(position).getDescription()!=null){
                holder.description.setText(footballArray.get(position).getDescription());

            }
            if(footballArray.get(position).getUpdates()!=null){
                String updated=footballArray.get(position).getUpdates();
                String[] publish=updated.split("T");
                if(!publish[0].equalsIgnoreCase("null")){
                    holder.date.setText(publish[0]);
                }else{
                    holder.date.setText("N/A");
                }
            }
            //holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);



        }

        @Override
        public int getItemCount() {
            if(footballArray==null)return 0;
            else
                return footballArray.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    footballArray = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            footballArray1=originalList;

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
            footballArray1=results;
            return results;
        }

    }


    public static void SearchPlayer(String text)
    {
        Log.v("2nd",text+"2");
        adapter.getFilter().filter(text);

    }

}
