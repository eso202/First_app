package com.example.laptophome.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Main_page extends AppCompatActivity  implements View.OnClickListener,SearchView.OnQueryTextListener{
FirebaseUser firebaseUser;
FirebaseAuth firebaseAuth;
TextView welcome;
Button logout,sell;
ImageButton nextbtn,backbtn;
ViewPager viewPager;
RecyclerView recyclerView;
recycle adapter;
ArrayList<view_pager_item> view_pager_items;
ArrayList<laptop_item> recycle_array;
    sqllite sqllite;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        firebaseAuth=FirebaseAuth.getInstance();
        searchView=(SearchView)findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        firebaseUser=firebaseAuth.getCurrentUser();
        welcome=(TextView)findViewById(R.id.displa_name);
        welcome.setText("welcome "+firebaseUser.getDisplayName());
        logout=(Button)findViewById(R.id.Log_out);
        sell=(Button)findViewById(R.id.make_product);
        logout.setOnClickListener(this);
        sell.setOnClickListener(this);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        nextbtn=(ImageButton)findViewById(R.id.imageButton2);
        backbtn=(ImageButton)findViewById(R.id.imageButton3);
        //////////////////////////////View Pager////////////////////////////////////////////////////////////////////////
        view_pager_items=new ArrayList<>();
        view_pager_items.add(new view_pager_item("we have Laptops textt text text text",R.drawable.lapoo));
        view_pager_items.add(new view_pager_item("you can buy and sell all kinds of printers text text",R.drawable.lapo4));
        view_pager_items.add(new view_pager_item("you can buy and sell all accessories of laptops like camera ,headphones",R.drawable.lapo3));
        pageadpater pageadpater=new pageadpater(view_pager_items,this);
        viewPager.setAdapter(pageadpater);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if(position==0)
                        {
                            nextbtn.setVisibility(View.VISIBLE);
                            backbtn.setVisibility(View.INVISIBLE);
                        }

                        if(position==1)
                        {
                            nextbtn.setVisibility(View.VISIBLE);
                            backbtn.setVisibility(View.VISIBLE);
                        }

                        if(position==2)
                        {
                            nextbtn.setVisibility(View.INVISIBLE);
                            backbtn.setVisibility(View.VISIBLE);
                        }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////
        ////////////////////////RecycleView//////////////////////////////////////////////////////


        recyclerView=(RecyclerView)findViewById(R.id.recycle22);
      sqllite=new sqllite(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recycle_array=sqllite.getrecords();
        adapter=new recycle(this,recycle_array);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);





    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        switch (id)

        {
            case R.id.Log_out:
                firebaseAuth.signOut();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.make_product:
                startActivity(new Intent(this,third_page.class));
                break;


        }

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public void nextpage(View view) {
        viewPager.setCurrentItem(getItem(+1), true);
    }

    public void backpage(View view) {
        viewPager.setCurrentItem(getItem(-1), true);
    }


    //////////////////////////////////Search View///////////////////////////////////////////////////
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userinput=newText.toLowerCase();
        List<laptop_item>momo=new ArrayList<>();
        for(laptop_item laptop_item:recycle_array)
        {
            if(laptop_item.getTitle().toLowerCase().contains(userinput))
            {
                momo.add(laptop_item);
            }


        }
        adapter.arraylist(momo);
        return true;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
                //////////// PAGE ADAPTER CLASS////////////////////////////////
    public class pageadpater extends PagerAdapter
    {
            ArrayList<view_pager_item> array11=new ArrayList<>();
            Context mcontext;
            LayoutInflater layoutInflater;

        public pageadpater(ArrayList<view_pager_item> array, Context mcontext) {
            this.array11 = array;
            this.mcontext = mcontext;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager vp=(ViewPager)container;
            View vv=(View)object;
            vp.removeView(vv);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vv=layoutInflater.inflate(R.layout.view_pager_design,null,false);
            ImageView img=(ImageView)vv.findViewById(R.id.imageView2);
            TextView txt=(TextView)vv.findViewById(R.id.textView3);
            img.setImageResource(array11.get(position).getPic());
            txt.setText(array11.get(position).getText());
            ViewPager vp=(ViewPager)container;
            vp.addView(vv,0);

            return vv;
        }

        @Override
        public int getCount() {
            return array11.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==object);
        }
    }


}