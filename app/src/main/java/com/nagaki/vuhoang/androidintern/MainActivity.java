package com.nagaki.vuhoang.androidintern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public AssetManager assetManager;
    public List<String> imgs;
    public String card_back = "src/cards back/bb.png";
    public String card_up = "src/cards/";
    public FloatingActionButton shuffle, back;
    float xDown =0 , yDown = 0;
    private GestureDetector gestureDetector;
    ConstraintLayout main;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLayOutID();

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        try {
            // get list of images of card_up from assets folder
            imgs = getImage(this.getApplicationContext());
            // shuffle order
            Collections.shuffle(imgs);


            for(int i = 0; i <= imgs.size() - 1; i++ ){
                ImageView card = new ImageView(this.getApplicationContext());
                card.setImageBitmap(setCardImage(this.getApplicationContext(), card_back));
                main.addView(card);
                int finalI = i;
                card.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (gestureDetector.onTouchEvent(event)) {
                            // single tap
                            try {
                                card.setImageBitmap(setCardImage(v.getContext(), card_up+ imgs.get(finalI)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // for move and drag
                            switch (event.getActionMasked()){

                                case MotionEvent.ACTION_DOWN:
                                    xDown = event.getX();
                                    yDown = event.getY();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    float moveX, moveY;
                                    moveX = event.getX();
                                    moveY = event.getY();

                                    float distanceX = moveX - xDown;
                                    float distanceY = moveY - yDown;

                                    card.setX(card.getX()+distanceX);
                                    card.setY(card.getY()+distanceY);

                                    break;
                            }
                        }

                        return true;
                    }
                });
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        // action for drag and drop


        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                overridePendingTransition( 0, 0);
                startActivity(intent);
                overridePendingTransition( 0, 0);
            }
        });

        back.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        });

    }

    public Bitmap setCardImage(Context context, String card_path) throws IOException {
        assetManager = context.getAssets();
        InputStream is = assetManager.open(card_path);
        return BitmapFactory.decodeStream(is);
    }


    public void getLayOutID(){
        shuffle = (FloatingActionButton) findViewById(R.id.shuffle);
        main = (ConstraintLayout) findViewById(R.id.main);
        back = (FloatingActionButton) findViewById(R.id.back);
    }

    private List<String> getImage(Context context) throws IOException {
        assetManager = context.getAssets();
        return Arrays.asList(assetManager.list("src/cards"));
    }

    private static class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}