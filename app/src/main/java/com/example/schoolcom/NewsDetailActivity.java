package com.example.schoolcom;
/*
*created by prakash
* Activity for Detailed news
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class NewsDetailActivity extends AppCompatActivity
{

    //CollapsingToolbarLayout collapsingToolbar;
    public String Description,title,url,updated,imageUrl,Description1,title1,url1,updated1,imageUrl1,Description2,title2,url2,updated2,imageUrl2;

    ImageView floatingActionButton;
    Button readMore;
    TextView description;
    ImageView imageView;
    TextView updatesat;
    private static ViewPager mPager;
    private TabLayout tabLayout;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //private static  Integer[] IMAGES;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ArrayList<String> DescArray = new ArrayList<String>();
    private ArrayList<String> ReadMoreArray = new ArrayList<String>();

    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        description=(TextView)findViewById(R.id.place_detail);
        imageView= (ImageView) findViewById(R.id.image);
        updatesat=(TextView)findViewById(R.id.place_location);
        readMore=(Button)findViewById(R.id.readMore);
        floatingActionButton=(ImageView) findViewById(R.id.fab);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Drawable image;
        Intent intent=getIntent();
        Description=intent.getStringExtra("DES");
        title=intent.getStringExtra("TITLE");
        imageUrl=intent.getStringExtra("IMAGEURL");
        updated=intent.getStringExtra("UPDATED");
        url=intent.getStringExtra("LINK");

        String[] publish=updated.split("T");
        //toolbar heading
        String text = intent.getStringExtra("text");
        TextView textView = (TextView)findViewById(R.id.news_type);

        textView.setText(text);

if(publish[0]!= null || publish[0].equals("")) {
    updatesat.setText(publish[0]);
}
        description.setText(title+". "+Description);
        Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareInfromation(title,url);
            }
        });


    }



    public void shareInfromation(String name, String url) {
        String mimeType = "text/plain";
        String Title = "Choose From...";
        Uri myUri = Uri.parse(url);
        ShareCompat.IntentBuilder.from(NewsDetailActivity.this)
                .setType(mimeType)
                .setChooserTitle(Title)
                .setText(name+" "+myUri)
                .startChooser();
    }

    public void backClick(View view) {
        finish();
    }

    /*Views intialization*/
    private void init() {
        Intent intent=getIntent();
        Description=intent.getStringExtra("DES");
        title=intent.getStringExtra("TITLE");
        imageUrl=intent.getStringExtra("IMAGEURL");
        updated=intent.getStringExtra("UPDATED");
        url=intent.getStringExtra("LINK");
        Description1=intent.getStringExtra("DES1");
        title1=intent.getStringExtra("TITLE1");
        imageUrl1=intent.getStringExtra("IMAGEURL1");
        updated1=intent.getStringExtra("UPDATED1");
        url1=intent.getStringExtra("LINK1");
        // Toast.makeText(NewsDetailActivity.this, url1, Toast.LENGTH_LONG).show();
        Description2=intent.getStringExtra("DES2");
        title2=intent.getStringExtra("TITLE2");
        imageUrl2=intent.getStringExtra("IMAGEURL2");
        updated2=intent.getStringExtra("UPDATED2");
        url2=intent.getStringExtra("LINK2");
        String[] IMAGES= {imageUrl,imageUrl1,imageUrl2};
        String[] DESC={Description,Description1,Description2};
        String[] READMORE={url,url1,url2};
        for(int i=0;i<IMAGES.length;i++)
        { ImagesArray.add(IMAGES[i]);
            DescArray.add(DESC[i]);
            ReadMoreArray.add(READMORE[i]);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        tabLayout=(TabLayout)findViewById(R.id.indicator2);


        mPager.setAdapter(new NewsDetailAdapter(NewsDetailActivity.this,ImagesArray,DescArray,ReadMoreArray));
        tabLayout.setupWithViewPager(mPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);


        final float density = getResources().getDisplayMetrics().density;


        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager

        // Pager listener over indicator
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            NewsDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mPager.getCurrentItem() < DescArray.size() - 1) {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    } else {
                        mPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}


