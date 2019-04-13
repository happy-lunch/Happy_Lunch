package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


public class HomePage extends Fragment {

    public static List<Food> foods = getData();
    public static final int numPopularItem = 6;

    public static final int numCategories = 6;
    private int[] imgCategories = {R.drawable.ic_com, R.drawable.ic_mi, R.drawable.ic_banh_bao, R.drawable.ic_banh_mi, R.drawable.ic_sandwich, R.drawable.ic_trang_mieng};
    private KindOfFood[] kindImgCategoris = {
            KindOfFood.Com, KindOfFood.Mi, KindOfFood.Banh_Bao, KindOfFood.Banh_Mi, KindOfFood.Sandwich, KindOfFood.Trang_Mieng
    };

    private static int currentItem;

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
        grid.setAdapter(new FoodAdapter(getActivity(), foods));

        //---------------------------------------------------------------

        setUpPopularView();

        linearCategary = (LinearLayout) view.findViewById(R.id.linearCategory);
        //itemCategoriesView = inflater.inflate(R.layout.item_category_layout, null);
        setUpCategories();

        return view;
    }

    private static List<Food> getData(){

        List<Food> foods = new ArrayList<Food>();

        //Com

        Food com1 = new Food(R.drawable.com_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Com);
        Food com2 = new Food(R.drawable.com_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Com);
        Food com3 = new Food(R.drawable.com_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Com);
        Food com4 = new Food(R.drawable.com_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Com);
        Food com5 = new Food(R.drawable.com_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Com);

        foods.add(com1);
        foods.add(com2);
        foods.add(com3);
        foods.add(com4);
        foods.add(com5);

        //Mi

        Food mi1 = new Food(R.drawable.mi_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi2 = new Food(R.drawable.mi_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi3 = new Food(R.drawable.mi_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi4 = new Food(R.drawable.mi_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi5 = new Food(R.drawable.mi_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi6 = new Food(R.drawable.mi_6, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi7 = new Food(R.drawable.mi_7, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);
        Food mi8 = new Food(R.drawable.mi_8, "Mi xao bo", "15.000", 10 ,KindOfFood.Mi);

        foods.add(mi1);
        foods.add(mi2);
        foods.add(mi3);
        foods.add(mi4);
        foods.add(mi5);
        foods.add(mi6);
        foods.add(mi7);
        foods.add(mi8);

        //Banh bao

        Food banhbao1 = new Food(R.drawable.banh_bao_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);
        Food banhbao2 = new Food(R.drawable.banh_bao_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);
        Food banhbao3 = new Food(R.drawable.banh_bao_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);
        Food banhbao4 = new Food(R.drawable.banh_bao_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);
        Food banhbao5 = new Food(R.drawable.banh_bao_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);
        Food banhbao6 = new Food(R.drawable.banh_bao_6, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Bao);

        foods.add(banhbao1);
        foods.add(banhbao2);
        foods.add(banhbao3);
        foods.add(banhbao4);
        foods.add(banhbao5);
        foods.add(banhbao6);

        //Banh mi

        Food banhmi1 = new Food(R.drawable.banh_mi_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi2 = new Food(R.drawable.banh_mi_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi3 = new Food(R.drawable.banh_mi_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi4 = new Food(R.drawable.banh_mi_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi5 = new Food(R.drawable.banh_mi_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi6 = new Food(R.drawable.banh_mi_6, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);
        Food banhmi7 = new Food(R.drawable.banh_mi_7, "Mi xao bo", "15.000", 10 ,KindOfFood.Banh_Mi);

        foods.add(banhmi1);
        foods.add(banhmi2);
        foods.add(banhmi3);
        foods.add(banhmi4);
        foods.add(banhmi5);
        foods.add(banhmi6);
        foods.add(banhmi7);

        //Sandwich

        Food sandwich1 = new Food(R.drawable.sandwich_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich2 = new Food(R.drawable.sandwich_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich3 = new Food(R.drawable.sandwich_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich4 = new Food(R.drawable.sandwich_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich5 = new Food(R.drawable.sandwich_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich6 = new Food(R.drawable.sandwich_6, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);
        Food sandwich7 = new Food(R.drawable.sandwich_7, "Mi xao bo", "15.000", 10 ,KindOfFood.Sandwich);

        foods.add(sandwich1);
        foods.add(sandwich2);
        foods.add(sandwich3);
        foods.add(sandwich4);
        foods.add(sandwich5);
        foods.add(sandwich6);
        foods.add(sandwich7);


        //Trang mieng

        Food trangmieng1 = new Food(R.drawable.trang_mieng_1, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng2 = new Food(R.drawable.trang_mieng_2, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng3 = new Food(R.drawable.trang_mieng_3, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng4 = new Food(R.drawable.trang_mieng_4, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng5 = new Food(R.drawable.trang_mieng_5, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng6 = new Food(R.drawable.trang_mieng_6, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng7 = new Food(R.drawable.trang_mieng_7, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);
        Food trangmieng8 = new Food(R.drawable.trang_mieng_8, "Mi xao bo", "15.000", 10 ,KindOfFood.Trang_Mieng);

        foods.add(trangmieng1);
        foods.add(trangmieng2);
        foods.add(trangmieng3);
        foods.add(trangmieng4);
        foods.add(trangmieng5);
        foods.add(trangmieng6);
        foods.add(trangmieng7);
        foods.add(trangmieng8);

        return foods;
    }

    private void setUpPopularView(){
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewAdapter = new popularViewPagerAdapter(getActivity());
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
        /*/Auto swipe
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
        /*///---------------------------------------------------------------------------

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

        //ImageView img = (ImageView) itemCategoriesView.findViewById(R.id.iconCategories);

        for(int i=0;i<numCategories;i++){
            FoodImageView img = new FoodImageView(getActivity(), kindImgCategoris[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
            params.setMargins(8,8,8,8);
            img.setImageResource(imgCategories[i]);
            final KindOfFood k = img.getKind();
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getContext(), ListFoodsInCategory.class);
                    in.putExtra("KindOfFood", k);
                    startActivity(in);

                }
            });
            linearCategary.addView(img, params);
        }
    }

}
