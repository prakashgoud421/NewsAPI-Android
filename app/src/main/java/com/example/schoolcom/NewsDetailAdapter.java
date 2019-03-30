package com.example.schoolcom;



/**
 * Created by prakash
 */

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class NewsDetailAdapter extends PagerAdapter {


    private ArrayList<String> IMAGES;
    private ArrayList<String> DESC;
    private ArrayList<String> READMORE;
    private LayoutInflater inflater;
    private Context context;


    public NewsDetailAdapter(Context context,ArrayList<String> IMAGES,ArrayList<String>DESC,ArrayList<String>READMORE) {
        this.context = context;
        this.IMAGES=IMAGES;
        this.DESC=DESC;
        this.READMORE=READMORE;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.view_img_view_newsdetail, view, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        final TextView desc=(TextView)imageLayout.findViewById(R.id.desc1);
        final TextView readMore1=(TextView)imageLayout.findViewById(R.id.readmore1);
        readMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=READMORE.get(position);
                String des=DESC.get(position);

            }
        });
        //  Log.e("string",DESC.get(position));
        desc.setText(DESC.get(position));
        Picasso.with(context)
                .load(IMAGES.get(position))
                .resize(200,220)
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
