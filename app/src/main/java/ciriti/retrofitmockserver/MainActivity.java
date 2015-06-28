package ciriti.retrofitmockserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ciriti.retrofitmockserver.api.Api;
import ciriti.retrofitmockserver.bean.RespBean;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.resp)
    TextView responseTv;

    @InjectView(R.id.switch1)
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "" + isChecked, Toast.LENGTH_SHORT).show();
                responseTv.setText("");
                // this line uses RxAndroid to make a call
//                executeCallWithRxAndroid(isChecked);
                executeCallWithRetrofitCallback(isChecked);
            }
        });
    }

    /**
     * Service call using RxAndroid
     * @param isMock
     */
    public void executeCallWithRxAndroid(boolean isMock) {
        fetchUsers(isMock).subscribe(new Observer<RespBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplicationContext(), "Completed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RespBean respBean) {
                String json = new Gson().toJson(respBean);
                responseTv.setText(json);
            }
        });

    }

    /**
     * Service call using retrofit callback
     * @param isMock
     */
    public void executeCallWithRetrofitCallback(boolean isMock) {

        Api.getInstance().stackExchangeService(isMock).getUsers(10, new Callback<RespBean>() {
            @Override
            public void success(RespBean respBean, Response response) {
                String json = new Gson().toJson(respBean);
                responseTv.setText(json);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private Observable<RespBean> fetchUsers(final boolean isMock) {
        Observable<RespBean> observable = Observable
                .create(new Observable.OnSubscribe<RespBean>() {
                    @Override
                    public void call(Subscriber<? super RespBean> subscriber) {
                        try{
                            subscriber.onNext(Api.getInstance().stackExchangeService(isMock).getUsers(10));
                            subscriber.onCompleted();
                        }catch (Exception e){
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    @OnClick(R.id.button)
    public void click(){
        responseTv.setText("");
        executeCallWithRetrofitCallback(true);
    }
}
