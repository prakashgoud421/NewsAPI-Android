package com.example.schoolcom.Models.news;

import android.content.Context;
import android.os.AsyncTask;


import com.example.schoolcom.news.Politics;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by prakash
 */

public class HandleXml extends AsyncTask<Object, Object, Document> {
    private final Context context;
    List<FeedItem> list;
    URL url;
    ArrayList<FeedItem> feeditemArray = new ArrayList<>();

    public HandleXml(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Document aVoid) {

        Politics.adapter = new Politics.ContentAdapter(context, list);
        Politics.adapter.notifyDataSetChanged();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Document doInBackground(Object... voids) {
        Document document = null;

        try {
            // URL for India News for All catagories
            url = new URL("http://newsrack.in/crawled.feeds/toi.rss.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return document;
    }
}


