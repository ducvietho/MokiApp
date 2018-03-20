package com.example.ducvietho.moki.screen.activities.status;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Status;
import com.example.ducvietho.moki.screen.activities.category.CategoryActivity;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StatusActivity extends AppCompatActivity implements OnItemtClick<Status>,View.OnClickListener {
    @BindView(R.id.imgLeft)
    CircleImageView back;
    @BindView(R.id.rec_status)
    RecyclerView mRecyclerView;
    private List<Status> mList = new ArrayList<>();
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,StatusActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(StatusActivity.this);
        back.setOnClickListener(this);
        initStatus();
    }

    @Override
    public void onClick(Status status) {
        Intent intent =  getIntent();
        intent.putExtra("status", status.getName());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgLeft:
                finish();
                break;
            default:
                break;
        }
    }
    private void initStatus(){
        mList.add(new Status("Mới","Sản phẩm mới 100% nguyên seal chưa qua sử dụng"));
        mList.add(new Status("Gần như mới","Sản phẩm dùng lướt-chất lượng như mới"));
        mList.add(new Status("Tốt","Sản phẩm đã qua sử dụng-chất lượng cao"));
        mList.add(new Status("Khá tốt","Sản phẩm đã qua sử dụng-chất lượng đảm bảo"));
        mList.add(new Status("Cũ","Sản phẩm cũ"));
        mList.add(new Status("Cũ","Sản phẩm cũ"));
        GridLayoutManager manager = new GridLayoutManager(StatusActivity.this,1);
        mRecyclerView.setLayoutManager(manager);
        StatusAdapter adapter = new StatusAdapter(mList,StatusActivity.this);
        mRecyclerView.setAdapter(adapter);
    }
}
