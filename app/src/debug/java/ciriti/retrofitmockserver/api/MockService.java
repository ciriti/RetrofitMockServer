package ciriti.retrofitmockserver.api;

import com.google.gson.Gson;

import java.util.Collections;

import ciriti.retrofitmockserver.bean.RespBean;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;

/**
 * Created by carmelo.iriti on 26/06/2015.
 */
public class MockService implements  ApiService{

    @Override
    public RespBean getUsers(@Query("pagesize") int numItems) {
        return new Gson().fromJson(DATA, RespBean.class);
    }

        @Override
        public void getUsers(@Query("pagesize") int numItems, Callback<RespBean> callback) {
                RespBean obj = new Gson().fromJson(DATA, RespBean.class);
                Response response = new Response(Api.URL_ENDPOINT,
                        200,
                        "nothing",
                        Collections.EMPTY_LIST,
                        new TypedByteArray("application/json", DATA.getBytes()));
                callback.success(obj, response);
        }

        public static final String DATA = "{\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"badge_counts\": {\n" +
            "        \"bronze\": 100,\n" +
            "        \"silver\": 100,\n" +
            "        \"gold\": 100\n" +
            "      },\n" +
            "      \"account_id\": 10000,\n" +
            "      \"is_employee\": false,\n" +
            "      \"last_modified_date\": 1435175902,\n" +
            "      \"last_access_date\": 1435322729,\n" +
            "      \"age\": 30,\n" +
            "      \"reputation_change_year\": 1000,\n" +
            "      \"reputation_change_quarter\": 1000,\n" +
            "      \"reputation_change_month\": 1000,\n" +
            "      \"reputation_change_week\": 1000,\n" +
            "      \"reputation_change_day\": 1000,\n" +
            "      \"reputation\": 1000,\n" +
            "      \"creation_date\": 1222430705,\n" +
            "      \"user_type\": \"registered\",\n" +
            "      \"user_id\": 1000,\n" +
            "      \"accept_rate\": 1000,\n" +
            "      \"location\": \"Rome, Italy\",\n" +
            "      \"website_url\": \"http://google.com\",\n" +
            "      \"link\": 1000,\n" +
            "      \"profile_image\": \"http://blogs-images.forbes.com/thomasbrewster/files/2014/09/Android1.png\",\n" +
            "      \"display_name\": \"Fake User\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"has_more\": true,\n" +
            "  \"quota_max\": 300,\n" +
            "  \"quota_remaining\": 300\n" +
            "}";
}
