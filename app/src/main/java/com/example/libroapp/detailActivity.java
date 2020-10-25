package com.example.libroapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView nombre= findViewById(R.id.nombrelibroL_textView);
        TextView genero= findViewById(R.id.generoL_textView);
        TextView Fecha= findViewById(R.id.fechaL_textView);
        TextView noPaginas= findViewById(R.id.paginasL_textView);
        TextView pais= findViewById(R.id.paisL_textView);
        TextView editorial= findViewById(R.id.editorialL_textView);
        TextView edicion= findViewById(R.id.edicionL_textView);
        TextView autores= findViewById(R.id.autoresL_textView);
        TextView ISBN= findViewById(R.id.isbnL_textView);
        TextView apa= findViewById(R.id.apaL_textView);



        Intent in = getIntent();
        Intent autor1 = getIntent();
        int index = in.getIntExtra("INDEX", -1);
        String autor = autor1.getStringExtra("nombreLibro");
        TextView nombrelibro = findViewById(R.id.nombrelibroL_textView);
        nombrelibro.setText(autor);
        if (index > -1) {
            int pic = getImg(index);
            ImageView img = (ImageView) findViewById(R.id.imageView2);
            scaleImg(img, pic);
        }

    }

    private int getImg(int index) {
        switch (index) {
            case 0:
                return R.drawable.ic_dashboard_black_24dp;
            case 1:
                return R.drawable.ic_notifications_black_24dp;
            case 2:
                return R.drawable.ic_notifications_black_24dp;
            default:
                return -1;
        }
    }

    private void scaleImg(ImageView img, int pic) {
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();
        if (imgWidth > screenWidth) {
            int ratio = Math.round((float) imgWidth / (float) screenWidth);
            options.inSampleSize = ratio;

        }
        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);


    }
}
