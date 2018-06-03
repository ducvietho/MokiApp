package com.example.ducvietho.moki.screen.activities.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Message;
import com.example.ducvietho.moki.data.model.MessageResponse;
import com.example.ducvietho.moki.data.model.Messages;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ConversationDataRepository;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ConversationRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.buy.BuyActivity;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ru.noties.scrollable.ScrollableLayout;

import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID_CUSTOMER;
import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID_PRODUCT;
import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID_SELLER;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.txtSeller)
    FontTextView mSeller;
    @BindView(R.id.txtName)
    FontTextView mName;
    @BindView(R.id.txtPrice)
    FontTextView mPrice;
    @BindView(R.id.btnNavLeft)
    ImageButton mBtnBack;
    @BindView(R.id.btnBuyNow)
    FontTextView mBuy;
    @BindView(R.id.edtComment)
    EditText edtChat;
    @BindView(R.id.btnSent)
    Button mBtnSend;
    @BindView(R.id.img_product)
    ImageView mImageProduct;
    @BindView(R.id.scrollable_layout)
    ScrollableLayout scroll;
    @BindView(R.id.recycle_chat)
    RecyclerView mRecyclerView;
    @BindView(R.id.messInstruct)
    FontTextView mMessInstruct;
    @BindView(R.id.btnDelete)
    Button delete;
    private List<Message> mList = new ArrayList<>();
    private DialogLoading mLoading;
    private ProductDataRepository mRepository;
    private ConversationDataRepository mConversationDataRepository;
    private CompositeDisposable mDisposable;
    private ChatRecyclerAdapter adapter;
    int idCustomer, idSeller, idProduct,idConversation;
    private UserSession mUserSession;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.URL_SOCKET);
        } catch (URISyntaxException e) {}
    }
    public static Intent getIntent(Context context, int idSeller, int idCustomer, int idProduct) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_ID_SELLER, idSeller);
        intent.putExtra(EXTRA_ID_CUSTOMER, idCustomer);
        intent.putExtra(EXTRA_ID_PRODUCT, idProduct);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(ChatActivity.this);
        mSocket.on("message_response", onNewMessage);
        mSocket.connect();
        mUserSession = new UserSession(ChatActivity.this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mConversationDataRepository = new ConversationDataRepository(new ConversationRemoteDataResource
                (MokiServiceClient.getInstance()));
        mLoading = new DialogLoading(ChatActivity.this);
        mBtnBack.setOnClickListener(this);
        delete.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        mBuy.setOnClickListener(this);
        idCustomer = getIntent().getIntExtra(EXTRA_ID_CUSTOMER, 0);
        idSeller = getIntent().getIntExtra(EXTRA_ID_SELLER, 0);
        idProduct = getIntent().getIntExtra(EXTRA_ID_PRODUCT, 0);
        mLoading.showDialog();
        initProduct(idCustomer, idProduct);
        getMessagesConversation(idProduct,idSeller,idCustomer);
        edtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.scrollTo(0, scroll.getMaxScrollY());
            }
        });
        edtChat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    scroll.scrollTo(0, scroll.getMaxScrollY());
                }
            }
        });
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        edtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    delete.setVisibility(View.GONE);
                }
                else {
                    delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Gson().fromJson(args[0].toString(),Message.class);
                    if(message.getIdConversation()==idConversation){
                        mList.add(message);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(mList.size());
                    }

                }
            });
        }


    };
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnNavLeft:
                finish();
                break;
            case R.id.btnDelete:
                edtChat.setText("");
                break;
            case R.id.btnSent:
                if(edtChat.getText().toString()!=null){
                    sendMessageConversation(edtChat.getText().toString());
                }
                break;
            case R.id.btnBuyNow:
                startActivity(new BuyActivity().getIntent(ChatActivity.this,idProduct));
                break;
            default:
                break;
        }
    }

    private void initProduct(int idUser, int idProduct) {
        mDisposable.add(mRepository.getProductDetail(idProduct, idUser).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Product>() {
            @Override
            public void onNext(Product value) {
                mLoading.cancelDialog();
                String [] images = value.getImage().split(",");
                Picasso.with(ChatActivity.this).load(images[0]).into(mImageProduct);
                mSeller.setText(value.getUser().getName());
                mName.setText(value.getName());
                String price = String.valueOf(value.getPrice());
                if(price.length()<=6){
                    price = new StringBuilder(price).insert(price.length()-3,",").toString();
                    mPrice.setText(price+" VNĐ");
                }
            }

            @Override
            public void onError(Throwable e) {
                mLoading.cancelDialog();
                Toast.makeText(ChatActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
    private void getMessagesConversation(int idProduct,int idUser1,int idUser2){

        mDisposable.add(mConversationDataRepository.getMessagesConversation(idProduct,idUser1,idUser2).subscribeOn
                (Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Messages>() {
            @Override
            public void onNext(Messages value) {
                idConversation = value.getIdConver();
                if(value.getList().size()>0){
                    mMessInstruct.setVisibility(View.GONE);
                    GridLayoutManager manager = new GridLayoutManager(ChatActivity.this,1);
                    mRecyclerView.setLayoutManager(manager);
                    mList = value.getList();
                    adapter = new ChatRecyclerAdapter(mList,ChatActivity.this);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.smoothScrollToPosition(mList.size());
                }else {

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));

    }
    private void sendMessageConversation( final String message){
        edtChat.setText("");
        final DialogLoading loading = new DialogLoading(ChatActivity.this);
        loading.showDialog();
        mDisposable.add(mConversationDataRepository.setMessageConversation(idConversation,mUserSession.getUserDetail().getId(),message)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MessageResponse>() {
                    @Override
                    public void onNext(MessageResponse value) {
                        loading.cancelDialog();
                        Message messa = new Message();
                        messa.setIdConversation(idConversation);
                        messa.setSender(mUserSession.getUserDetail());
                        messa.setMessage(message);
                        String msg = new Gson().toJson(messa);
                        mSocket.emit("message",msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ChatActivity.this, "Error "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
