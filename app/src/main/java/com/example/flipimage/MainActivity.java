package com.example.flipimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flipimage.Api.GameApi;
import com.example.flipimage.DataBase.MySqlite;
import com.example.flipimage.Models.Data;
import com.example.flipimage.Models.GameImage;
import com.example.flipimage.Models.MainData;
import com.example.flipimage.Models.Picinfo;
import com.example.flipimage.Models.TableInfo;
import com.example.flipimage.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IMAGE_BASE_URL = "https://jbmatrix.in/dev2/screenzyapp/screenzyapp/";

    String a1 = null;
    String imgID = null;
    ImageView z1 = null;
    ImageView z2 = null;
    int c;
    int m;

    int i = 0;
    MySqlite mySqlite;
    ImageView prevImg;
    GameApi gameApi;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<Integer> imageIds = new ArrayList<>();

    ArrayList<ImageView> imageViews = new ArrayList<>();

    ArrayList<Picinfo> picinfos = new ArrayList<>();

    ArrayList<ImageView> closeImages = new ArrayList();
    ArrayList<String> stringappend = new ArrayList<>();

    ArrayList<ImageView> imageViewsArr = new ArrayList<>();

    LinearLayout.LayoutParams layoutParams3;
    LinearLayout.LayoutParams layoutParams4;

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    FrameLayout frameLayout;

    List<GameImage> gameImages = new ArrayList<>();

    int flag = 0;

    boolean isCompleted;

    CardView cardView;

    boolean isOpen;

    private LinearLayout finallinearlayout1;
    private LinearLayout finallinearlayout2;
    private LinearLayout.LayoutParams finallayoutparams3;
    private  LinearLayout.LayoutParams finallayoutparams4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View frameLayout = initViews();

        setContentView(frameLayout);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Initializing Game...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDatas();

            }
        }, 1000);

    }

    private void getDatas() {


        gameApi = RetrofitClient.getInstance().getApi();

        Call<MainData> mainDataCall = gameApi.getMainData();
        mainDataCall.enqueue(new Callback<MainData>() {
            @Override
            public void onResponse(Call<MainData> call, Response<MainData> response) {

                gameImages = new ArrayList<>();
                int i = 0;

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                MainData mainData = (MainData) response.body();
                Log.d(MainActivity.class.getName(), "onResponse:" + mainData);

                if (mainData != null) {

                    List<Data> dataList = mainData.getData();

                    for (Data data : dataList) {

                        gameImages = data.getGameImage();

                    }

                    doubleImages(gameImages);

                }


            }

            @Override
            public void onFailure(Call<MainData> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void openFirst() {


                for (ImageView imageView : imageViewsArr) {


                    String uri = (((String) imageView.getTag()).substring(0 , ((String)imageView.getTag()).length()-1));

                    Glide.with(MainActivity.this).load(uri).into(imageView);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Animator flipOutAnimator = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_out);
                            flipOutAnimator.setTarget(imageView);
                            flipOutAnimator.start();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Glide.with(MainActivity.this).load(R.drawable.imageholder).into(imageView);

                                    float scale = getResources().getDisplayMetrics().density;
                                    imageView.setCameraDistance(8000*scale);

                                    Animator flipInAnimatorSet = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_in);
                                    flipInAnimatorSet.setTarget(imageView);
                                    flipInAnimatorSet.start();

                                }
                            } , 1000);

                        }
                    } , 5000);

                }

    }


    private void doubleImages(List<GameImage> gameImages) {

        loopImages(gameImages, 0, 3);

        progressDialog.dismiss();
    }

    private void loopImages(List<GameImage> gameImages, int l, int bound) {

        int count = 3;

        for (int i = l; i < bound; i++) {


            imageViewsArr.add(new ImageView(this));
            if (l >= 3) {

                imageViewsArr.get(i).setId(Integer.parseInt(gameImages.get(i - 3).getId()));
                imageViewsArr.get(i).setTag(IMAGE_BASE_URL + gameImages.get(i - 3).getImage() + "C");


            } else {

                imageViewsArr.get(i).setId(Integer.parseInt(gameImages.get(i).getId()));
                imageViewsArr.get(i).setTag(IMAGE_BASE_URL + gameImages.get(i).getImage() + "C");


            }

            imageViewsArr.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsArr.get(i).setOnClickListener(this);


        }


        if (flag == 1) {

            progressDialog.setProgress(100);

            Collections.shuffle(imageViewsArr);
            shuffle(imageViewsArr , 0 , 3);
            openFirst();

            return;
        }

        flag++;

        progressDialog.setProgress(50);

        loopImages(gameImages, 3, 6);
    }

    private void shuffle(ArrayList<ImageView> imageViews, int i, int bound) {

        for (int j = i ; j<bound ; j++) {

            linearLayout1.addView(imageViews.get(j));
            imageViews.get(j).setLayoutParams(layoutParams3);
            imageViews.get(j).setBackgroundResource(R.drawable.imageholder);

            if (j == 2) {

                linearLayout1 = linearLayout2;
                layoutParams3 = layoutParams4;

                shuffle(imageViews , 3 , 6);
            }


        }


        }

    private int generateUnique(int count) {

        do {
            if (count == 0) {

                count = 3;
            }
            count = new Random().nextInt(3);
        }while (linearLayout1.getChildAt(count) != null || count == 0);

        return count;
    }


    private View initViews() {

        cardView = new CardView(MainActivity.this);
        cardView.setRadius(10);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        frameLayout = new FrameLayout(MainActivity.this);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setBackgroundColor(getResources().getColor(R.color.black));

        linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(2);
        linearLayout.setLayoutParams(layoutParams);

        linearLayout1 = new LinearLayout(MainActivity.this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setId(View.generateViewId());
        linearLayout1.setWeightSum(3);
        linearLayout1.setBackgroundColor(getResources().getColor(R.color.grey));
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams1.leftMargin = 250;
        layoutParams1.topMargin = 5;
        layoutParams1.bottomMargin = 5;
        layoutParams1.gravity = Gravity.CENTER;
        layoutParams1.weight = 1;
        linearLayout1.setLayoutParams(layoutParams1);

        finallinearlayout1 = linearLayout1;


        linearLayout2 = new LinearLayout(MainActivity.this);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setId(View.generateViewId());
        linearLayout2.setWeightSum(3);
        linearLayout2.setBackgroundColor(getResources().getColor(R.color.grey));
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams2.weight = 1;
        layoutParams2.rightMargin = 300;
        layoutParams2.topMargin = 5;
        layoutParams2.bottomMargin = 5;
        layoutParams2.gravity = Gravity.CENTER;
        linearLayout2.setLayoutParams(layoutParams2);

        finallinearlayout2 = linearLayout2;

        linearLayout.addView(linearLayout1);
        linearLayout.addView(linearLayout2);

        layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams3.weight = 1;
        layoutParams3.width = 200;
        layoutParams3.height = 400;
        layoutParams3.setMargins(5, 20, 50, 5);
        layoutParams3.gravity = Gravity.END;

        finallayoutparams3 = layoutParams3;



        layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams4.weight = 1;
        layoutParams4.width = 200;
        layoutParams4.height = 400;
        layoutParams4.setMargins(5, 20, 50, 5);
        layoutParams4.gravity = Gravity.START;

        finallayoutparams4 = layoutParams4;

        frameLayout.addView(linearLayout);

        //Collections.shuffle(imageViews);

        return frameLayout;

    }


    private void insertData() {

        TableInfo tableInfo = new TableInfo();
        tableInfo.setId(1);
        tableInfo.setTGid(1234);
        tableInfo.setComID(1234);
        tableInfo.setGameid(1234);
        tableInfo.setPic1("djsdhsjdhs");
        tableInfo.setPic2("akjdjahdjahs");
        tableInfo.setPic3("ahbdhabdhabsd");
        tableInfo.setPic4("ajdjabhdbshjsa");
        tableInfo.setPic5("ajdjabhdb");
        tableInfo.setPic6("ajdjabhjsa");
        tableInfo.setStatus("Status");
        tableInfo.setLog("dabhjda");

        mySqlite.insertData(tableInfo);

        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        m++;
        c++;

        if (m ==1) {

            z1 = (ImageView) v;
            openImages(z1);
            closeImages.add((ImageView) v);

        }else if (m == 2 && c==2) {

            if (closeImages.size() == 0) {

                closeImages.add(z1);
            }

            z2 = (ImageView) v;
            openImages(z2);
            checkIds(z1 , z2);
            closeImages.add((ImageView) v);


        }else if (m ==3) {

            isOpen = true;

            z1 = (ImageView) v;

            Animator flipOutAnimator = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_out);
            flipOutAnimator.setTarget(v);
            flipOutAnimator.start();

            String uri = ((String) v.getTag()).substring(0, ((String) v.getTag()).length() - 1);

            if (isOpen) {

                for (ImageView imageView : imageViewsArr) {

                    imageView.setClickable(false);
                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    for (ImageView imageView : imageViewsArr) {

                        imageView.setClickable(true);
                    }
                 isOpen = false;
                }
            } , 1000);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    float scale = getResources().getDisplayMetrics().density;
                    v.setCameraDistance(8000*scale);

                    Animator flipInAnimatorSet = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_in);
                    flipInAnimatorSet.setTarget(v);
                    flipInAnimatorSet.start();

                    Glide.with(MainActivity.this).load(uri).into((ImageView) v);

                    closeImage(closeImages);

                }
            } , 500);



        }

        checkAllOpens();

    }

    private void checkIds(ImageView a1 , ImageView a2) {

        if (Integer.toString(a1.getId()).equals(Integer.toString(a2.getId()))) {

            int a1Index = imageViewsArr.indexOf(a1);
            int a2Index = imageViewsArr.indexOf(a2);

            a1.setClickable(false);
            a2.setClickable(false);

            String a1Uri = ((String) a1.getTag()).substring(0, ((String) a1.getTag()).length() - 1);
            String a2Uri = ((String) a2.getTag()).substring(0, ((String) a2.getTag()).length() - 1);

            a1.setTag(a1Uri + "OO");
            a2.setTag(a2Uri + "OO");

            imageViewsArr.set(a1Index , a1);
            imageViewsArr.set(a2Index , a2);



            //openImages(new ImageView[]{a1 , a2});

            closeImages.clear();
            c = 0;
            m = 0;
        } else {

            //openImages(new ImageView[]{a1 , a2});
        }
    }

    private void openImages(ImageView imageView) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (((String)imageView.getTag()).substring(((String)imageView.getTag()).length()-1).equals("O")) {

                    return;

                }

                imageView.setClickable(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        Animator flipOutAnimator = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_out);
                        flipOutAnimator.setTarget(imageView);
                        flipOutAnimator.start();


                    }
                });






                String uri = ((String) imageView.getTag()).substring(0, ((String) imageView.getTag()).length() - 1);

                //Glide.with(MainActivity.this).load(uri).into(imageView);

                int index = imageViewsArr.indexOf(imageView);
                imageView.setTag(uri + "O");
                imageViewsArr.set(index , imageView);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        float scale = getResources().getDisplayMetrics().density;
                        imageView.setCameraDistance(8000*scale);



                        Animator flipInAnimatorSet = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_in);
                        flipInAnimatorSet.setTarget(imageView);
                        flipInAnimatorSet.start();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Glide.with(MainActivity.this).load(uri).into(imageView);

                            }
                        });




                    }
                } , 500);


            }
        }).start();




    }



    private void checkAllOpens() {

        for (ImageView imageView : imageViewsArr) {

            if (((String) imageView.getTag()).substring(((String) imageView.getTag()).length()-2).equals("OO")) {

                isCompleted = true;

            } else {

                isCompleted = false;
                break;
            }
        }

        if (isCompleted) {

            for (ImageView imageView : imageViewsArr) {

                imageView.setClickable(false);

            }

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_toast , (ViewGroup) findViewById(R.id.custom_toast_layout));

            Toast toast = new Toast(MainActivity.this);
            toast.setGravity(Gravity.CENTER_VERTICAL , 0 , -50);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();

            resetGame();





        }
    }

    public void resetGame() {

        imageViewsArr.clear();
        flag = 0;




        linearLayout1 = finallinearlayout1;
        linearLayout2 = finallinearlayout2;

        linearLayout1.removeAllViews();
        linearLayout2.removeAllViews();

        layoutParams3 =  finallayoutparams3;
        layoutParams4 =  finallayoutparams4;

        progressDialog.setProgress(0);

        doubleImages(gameImages);




    }

    private void closeImage(ArrayList<ImageView> imageViews) {

        for (ImageView imageView : imageViews) {

            if (((String)imageView.getTag()).substring(((String)imageView.getTag()).length()-2).equals("OO")) {

                continue;
            }



            imageView.setClickable(false);

            Animator flipOutAnimator = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_out);
            flipOutAnimator.setTarget(imageView);
            flipOutAnimator.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String  uri = ((String) imageView.getTag()).substring(0 , ((String)imageView.getTag()).length()-1);
                    imageView.setTag(uri + "C");
                    //imageView.setClickable(true);

                    float scale = getResources().getDisplayMetrics().density;
                    imageView.setCameraDistance(8000*scale);

                    Animator flipInAnimatorSet = AnimatorInflater.loadAnimator(MainActivity.this , R.animator.flip_in);
                    flipInAnimatorSet.setTarget(imageView);
                    flipInAnimatorSet.start();

                    Glide.with(MainActivity.this).load(R.drawable.imageholder).into(imageView);

                }
            } , 500);


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            imageView.setClickable(true);

        }

        closeImages.clear();
        m = 1;
        c = 1;



        }

 }







