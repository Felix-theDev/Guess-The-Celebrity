package com.company.guessthecelebrity;
/** An app for guessing a celebrity from a list of options when their picture is displayed
 * @author Felix Ogbonnaya
 * @since 2020-10-10
 */
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> imageUrl;
    ArrayList<String> names;
    ImageView image;
    String name;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Bitmap bitmap;
    public class DownloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {


            imageUrl = new ArrayList<>();
            names = new ArrayList<>();

            String result = "";

            URL url;
            HttpURLConnection httpURLConnection;
            try{
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data!= -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("Progress", result);


            }catch (Exception e){
                e.printStackTrace();
                Log.i("Progress", "Failed at DownloadTask");
                return "Failed";
            }
          Pattern pattern = Pattern.compile("img alt=\"(.*?)\".*src=\"(.*?)\"");
            Matcher matcher = pattern.matcher(result);

            return "Successful";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        Log.i("Progress", "Ata");

        DownloadTask task = new DownloadTask();
        String result;
        try{
//           result = task.execute("http://www.posh24.se/kandisar").get();
            result = task.execute("https://www.imdb.com/list/ls052283250/").get();
//            result = task.execute("https://www.google.com/").get();


            Log.i("Progress", result);
//            start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void start(){
        int i;
        Random random = new Random();
        DownloadImage task = new DownloadImage();
        try{
            for(int number = 1; number<= 10; number++){

                i = random.nextInt((100)+1);
                Log.i("Progress", imageUrl.get(i));
//                String url = images.get(i);
//                bitmap = task.execute(url).get();
//                image.setImageBitmap(bitmap);
//                name = names.get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            }catch (Exception e){
                e.printStackTrace();

            }
            return null;
        }
    }


}
