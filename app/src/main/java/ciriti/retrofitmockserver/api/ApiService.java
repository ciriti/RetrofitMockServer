package ciriti.retrofitmockserver.api;

import ciriti.retrofitmockserver.bean.RespBean;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by carmelo.iriti on 26/06/2015.
 */
public interface ApiService {

    @Headers({
            "User-Agent: Retrofit-Mock-Sample"
    })
    @GET("/2.2/users?site=stackoverflow")
    public RespBean getUsers(@Query("pagesize") int numItems);

    @Headers({
            "User-Agent: Retrofit-Mock-Sample"
    })
    @GET("/2.2/users?site=stackoverflow")
    public void getUsers(@Query("pagesize") int numItems, Callback<RespBean> callback);

}
