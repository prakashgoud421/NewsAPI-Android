package com.example.schoolcom.news;


//created by prakash

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.schoolcom.MainNewsFragment;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.example.schoolcom.NewsDetailActivity;
import com.example.schoolcom.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.example.schoolcom.Models.news.DataClass;
import com.example.schoolcom.Models.news.HandleXml;
import com.example.schoolcom.Models.news.FeedItem;


import java.util.ArrayList;
import java.util.List;

public class Politics extends Fragment {
    public static Activity activity;

    public static Context context;
    ShimmerFrameLayout mShimmerViewContainer;
    private HandleXml obj;
    ProgressDialog progress;
    public static List<FeedItem> scienceArray;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    public Politics() {

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        progress = ProgressDialog.show(getActivity(), "",
                "loading....", true);
        progress.setCancelable(true);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainNewsFragment.flag&&progress!=null&&progress.isShowing())
            progress.dismiss();

    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.view_news_recycler, container, false);
        recyclerView.setNestedScrollingEnabled(false);
        mShimmerViewContainer=recyclerView.findViewById(R.id.shimmer_news);
        mShimmerViewContainer.setDuration(3000);


        HandleXml handleXml=new HandleXml(context);
        handleXml.execute();
        progress.dismiss();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
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

                    Intent intent=new Intent(context,NewsDetailActivity.class);
                    intent.putExtra("DES",scienceArray.get(pos).getDescription());
                    intent.putExtra("TITLE",scienceArray.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",scienceArray.get(pos).getThumbNailUrl());
                    intent.putExtra("UPDATED",scienceArray.get(pos).getDate());
                    intent.putExtra("LINK",scienceArray.get(pos).getLink());
try {
    intent.putExtra("DES1", scienceArray.get(pos + 1).getDescription());
    intent.putExtra("TITLE1", scienceArray.get(pos + 1).getTitle());
    intent.putExtra("IMAGEURL1", scienceArray.get(pos + 1).getThumbNailUrl());
    intent.putExtra("UPDATED1", scienceArray.get(pos + 1).getDate());
    intent.putExtra("LINK1", scienceArray.get(pos + 1).getLink());

    intent.putExtra("DES", scienceArray.get(pos + 2).getDescription());
    intent.putExtra("TITLE", scienceArray.get(pos + 2).getTitle());
    intent.putExtra("IMAGEURL", scienceArray.get(pos + 2).getThumbNailUrl());
    intent.putExtra("UPDATED", scienceArray.get(pos + 2).getDate());
    intent.putExtra("LINK", scienceArray.get(pos + 2).getLink());
}
catch (IndexOutOfBoundsException en){
    intent.putExtra("DES1", scienceArray.get(pos - 1).getDescription());
    intent.putExtra("TITLE1", scienceArray.get(pos - 1).getTitle());
    intent.putExtra("IMAGEURL1", scienceArray.get(pos - 1).getThumbNailUrl());
    intent.putExtra("UPDATED1", scienceArray.get(pos - 1).getDate());
    intent.putExtra("LINK1", scienceArray.get(pos - 1).getLink());

    intent.putExtra("DES", scienceArray.get(pos - 2).getDescription());
    intent.putExtra("TITLE", scienceArray.get(pos - 2).getTitle());
    intent.putExtra("IMAGEURL", scienceArray.get(pos - 2).getThumbNailUrl());
    intent.putExtra("UPDATED", scienceArray.get(pos - 2).getDate());
    intent.putExtra("LINK", scienceArray.get(pos - 2).getLink());
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

        List<FeedItem> scienceData;
        List<FeedItem> originalList;
        public ContentAdapter(Context context, List<FeedItem> scienceData) {

            this.scienceData=scienceData;
            scienceArray=scienceData;
            originalList=scienceData;
        }

        @Override
        public Politics.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            Glide.with(context).load(scienceData.get(position).getThumbNailUrl()).override(500, 200)
                    .fitCenter().into(holder.picture);
            //Picasso.with(context).load(scienceData.get(position).getUrltoImage()).into(holder.picture);
            holder.name.setText(scienceData.get(position).getTitle());
            holder.description.setText(scienceData.get(position).getDescription());
            String updated=scienceData.get(position).getDate();
            String[] publish=updated.split("T");
            holder.date.setText(publish[0]);
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
                    scienceData = (List<FeedItem>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<FeedItem> filteredResults = null;
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

        protected List<FeedItem> getFilteredResults(String constraint) {
            List<FeedItem> results = new ArrayList<>();

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

