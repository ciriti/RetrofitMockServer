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
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.resp)
    TextView response;

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
                response.setText("");
                executeCallWithRxAndroid(isChecked);
            }
        });
    }

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
                response.setText(json);
            }
        });
    }

    private Observable<RespBean> fetchUsers(final boolean isMock) {
        Observable<RespBean> observable = Observable
                .create(new Observable.OnSubscribe<RespBean>() {
                    @Override
                    public void call(Subscriber<? super RespBean> subscriber) {
                        try{
                            subscriber.onNext(new Api().stackExchangeService(isMock).getUsers(10));
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
        executeCallWithRxAndroid(true);
    }
}
