package pinterest;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader{
    private static Downloader instance;
    public static final String URL_FOR_PINTEREST_VIDEO_DOWNLOADING_WEBSITE = "https://www.expertstool.com/download-pinterest-video/";

    private Downloader(){}

    public static Downloader getInstance(){
        if(instance == null){
            instance = new Downloader();
        }
        return instance;
    }

    public InputFile download(String url){

        InputFile inputFile = null;

        try {
            Connection.Response  response =  Jsoup.connect(URL_FOR_PINTEREST_VIDEO_DOWNLOADING_WEBSITE)
                    .method(Connection.Method.POST)
                    .data("url", url)
                    .timeout(30*1000)
                    .execute();

            Document doc = response.parse();
            URL videoLink = new URL(doc.select("video").attr("src"));
            URLConnection connection = videoLink.openConnection();
            InputStream is = connection.getInputStream();

            inputFile = new InputFile();
            inputFile.setMedia(is, videoLink.getPath().toString().substring(25));

        } catch (IOException e){
            e.printStackTrace();
        }

        return inputFile;
    }
}
