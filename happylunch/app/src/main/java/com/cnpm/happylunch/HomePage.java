package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomePage extends Fragment {

    public static final int numPopularItem = 6;

    public static final int numCategories = App.categories.size();

    private ArrayList<FoodImageView> imgFoodInCategory ;

    private int currentItem;

    private LinearLayout linearCategary;

    //private View itemCategoriesView;
    private View view;
    private ViewPager viewPager;
    private popularViewPagerAdapter viewAdapter;
    private LinearLayout linearLayout;
    private int countDot = numPopularItem;
    private ImageView[] dots;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        GridView grid = (GridView) view.findViewById(R.id.gridView);
        grid.setAdapter(new FoodAdapter(getActivity(), App.foods));

        //---------------------------------------------------------------

        setUpPopularView();

        linearCategary = view.findViewById(R.id.linearCategory);
        setUpCategories();

        grid.setOnItemClickListener((parent, view, position, id) -> Order(App.foods.get(position)));

        return view;
    }

    public void Order(Food food){

        //FoodDetail.food = food;
        startActivity(new Intent(getContext(), FoodDetail.class));

    }

    private void setUpPopularView(){
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


        App.foods.sort((f1,f2)->{
            if(f1.getNumSold() < f2.getNumSold()){
                return 1;
            }else{
                return -1;
            }
        });
        ArrayList<Food> popularFood = new ArrayList<Food>();
        for(int i=0; i < numPopularItem; i++){
            popularFood.add(App.foods.get(i));
        }
        viewAdapter = new popularViewPagerAdapter(getActivity(), popularFood);
        viewPager.setAdapter(viewAdapter);

        int firstPosition = viewAdapter.getCount()/2;
        currentItem = firstPosition - firstPosition%numPopularItem;
        viewPager.setCurrentItem(currentItem);

        linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        dots = new ImageView[countDot];

        for(int i=0; i<countDot; i++){
            dots[i] = new ImageView(getActivity());
            dots[i].setImageResource(R.drawable.non_active_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            linearLayout.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.active_dot);

        //-------------------------------------------------------------------------
        //Auto swipe
        final ViewPager VP = view.findViewById(R.id.viewPager);

        final Handler hand = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                ++currentItem;
                VP.setCurrentItem(currentItem);
            }
        };

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                hand.post(run);
            }
        },5000 ,5000);
        ////---------------------------------------------------------------------------

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for(int j=0; j<countDot; j++){
                    dots[j].setImageResource(R.drawable.non_active_dot);
                }
                dots[i%countDot].setImageResource(R.drawable.active_dot);
                currentItem = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setUpCategories(){

        imgFoodInCategory = new ArrayList<FoodImageView>();

        for(int i = 0;i < numCategories;i++){
            imgFoodInCategory.add(new FoodImageView(getActivity(), App.categories.get(i)));
        }

        for(int i=0;i<numCategories;i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
            params.setMargins(8,8,8,8);

            final int index = i;

            Picasso.get().load(imgFoodInCategory.get(i).getCate().getImg()).into(imgFoodInCategory.get(i));
            imgFoodInCategory.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getContext(), ListFoodsInCategory.class);
                    in.putExtra("KindOfFood", imgFoodInCategory.get(index).getCate());
                    startActivity(in);
                }
            });
            linearCategary.addView(imgFoodInCategory.get(i), params);
        }
    }

}