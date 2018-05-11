package com.pdtrong.final_android.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pdtrong.final_android.Login.MainActivity;
import com.pdtrong.final_android.MapsActivity;
import com.pdtrong.final_android.R;
import com.pdtrong.final_android.Search.ItemAdapter.ItemAdapter;
import com.pdtrong.final_android.Search.ItemClickListen.OnRecyclerViewItemClickListener;
import com.pdtrong.final_android.Search.ItemInfo.ItemInfo;
import com.pdtrong.final_android.UserDatabase.UserInfo;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.tooltip.Tooltip;

public class ListSearch extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private RecyclerView recyclerView;
    private List<ItemInfo> itemsList = new ArrayList<>();;
    private ItemAdapter itemAdapter;
    private UserInfo userInfo;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        //Khởi tạo toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Không hiện tiêu đề
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        //Hiện nút back
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         userInfo = (UserInfo) getIntent().getParcelableExtra("userinfo");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemAdapter = new ItemAdapter(itemsList);
        itemAdapter.setOnItemClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        prepareMovieData();
    }
    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_listsearch, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.it_Info:
                Toast.makeText(this,""+userInfo.getFullname()+"\n"+userInfo.getBirthday()+"\n"+
                        userInfo.getPhone()+"\n"+userInfo.getUsername(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.it_Logout:
                this.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void prepareMovieData(){
        itemsList.add(new ItemInfo(R.drawable.hospital,"Bệnh viện"));
        itemsList.add(new ItemInfo(R.drawable.bank, "Ngân hàng"));
        itemsList.add(new ItemInfo(R.drawable.busstop, "Trạm xe bus"));
        itemsList.add(new ItemInfo(R.drawable.coffee, "Quán coffee"));
        itemsList.add(new ItemInfo(R.drawable.restaurant, "Nhà hàng"));
        itemsList.add(new ItemInfo(R.drawable.shop, "Tạm hóa"));
        itemsList.add(new ItemInfo(R.drawable.repair, "Tiệm sửa xe"));

        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewItemClicked( int position, int id ) {
        switch (id){
            case R.id.linear_layout_icon_recyclerview:
                Tooltip.make(this,
                    new Tooltip.Builder(101)
                            .anchor(recyclerView.getChildAt(position).findViewById(R.id.ib_list), Tooltip.Gravity.BOTTOM)
                            .closePolicy(new Tooltip.ClosePolicy()
                                    .insidePolicy(true, false)
                                    .outsidePolicy(true, false), 3000)
                            .activateDelay(800)
                            .showDelay(300)
                            .text("Danh sách đường ngắn nhất")
                            .maxWidth(500)
                            .withArrow(true)
                            .withOverlay(true)
                            .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                            .build()
            ).show();
                Tooltip.make(this,
                        new Tooltip.Builder(101)
                                .anchor(recyclerView.getChildAt(position).findViewById(R.id.ib_map), Tooltip.Gravity.TOP)
                                .closePolicy(new Tooltip.ClosePolicy()
                                        .insidePolicy(true, false)
                                        .outsidePolicy(true, false), 3000)
                                .activateDelay(800)
                                .showDelay(300)
                                .text("Vị trí của bạn")
                                .maxWidth(500)
                                .withArrow(true)
                                .withOverlay(true)
                                .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                                .build()
                ).show();
                break;
            case R.id.ib_list:
                Toast.makeText(getBaseContext(),"Click list"+position,Toast.LENGTH_LONG).show();
                break;
            case R.id.ib_map:
                switch (position){
                    case 0:
                        Intent intent = new Intent(this,MapsActivity.class);
                        this.startActivity(intent);
                break;}
                break;
        }
    }
}
