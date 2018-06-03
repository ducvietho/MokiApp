package com.example.ducvietho.moki.screen.activities.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.utils.customview.FontEditText;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DiaglogPrice;
import com.example.ducvietho.moki.utils.dialog.DialogNotInfor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btnNavLeft)
    ImageView mback;
    @BindView(R.id.edtKeyword)
    FontEditText edKeyWord;
    @BindView(R.id.txtPrice)
    FontTextView tvPrice;
    @BindView(R.id.btn_search)
    FontTextView tvSearch;
    @BindView(R.id.btnSearchVoice)
    ImageView mView;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mback.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        mView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNavLeft:
                finish();
                break;
            case R.id.btnNavRight:
                edKeyWord.setText("");
                tvPrice.setText("");
                break;
            case R.id.txtPrice:
                new DiaglogPrice(SearchActivity.this).showDialog(tvPrice);
                break;
            case R.id.btn_search:
                if(edKeyWord.getText().toString().matches("")||tvPrice.getText().toString().matches("")){
                    new DialogNotInfor(SearchActivity.this).showDialog("Bạn phải nhập thông tin tìm kiếm cụ thể");
                }else{
                    startActivity(new SearchResultActivity().getIntent(SearchActivity.this,edKeyWord.getText().toString()
                            ,tvPrice.getText().toString()));
                }

                break;
            case R.id.btnSearchVoice:

                break;
        }

    }
}
