# 1. Android - How create a mock server with Retrofit 

The purpose of this article is ONLY to show you how create a mock server with retrofit, if you need also an example of architecture you could take a glance to my article about DAGGER 2. Anyway this will be the start point for all other future project.  

When you are working on your android app could happen you need to test you server call but, how can you do that if the server apis aren't ready? 
The answer is to create a mock server as following.

Suppose you have built the following Api class, 
```java
public class Api {

    public static final String URL_ENDPOINT = "https://api.stackexchange.com";
    
    //...

    protected RestAdapter getAdapter(Endpoint endpoint, Client client){
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .build();
    }

    public ApiService stackExchangeService(){
        return getAdapter(getEndpoint(), getClient()).create(ApiService.class);
    }

    //....

}
```

what you have to do is add, in "stackExchangeService" method, the manage of the "MockRestAdapter". 
First of all you should add in your gradle file the dependency to retrofit mock object:
```gradle
compile 'com.squareup.retrofit:retrofit-mock:1.9.0'
```
now you shoud extend the interface where you've declared the service calls, suppose the following code is you interface
```java
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
```
you shoud get a class like this:
```java
public class MockService implements  ApiService{

    @Override
    public RespBean getUsers(@Query("pagesize") int numItems) {
        return new Gson().fromJson(DATA, RespBean.class);
    }

        @Override
        public void getUsers(@Query("pagesize") int numItems, Callback<RespBean> callback) {
                RespBean obj = new Gson().fromJson(DATA, RespBean.class);
                Response response = new Response(Api.URL_ENDPOINT, 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", DATA.getBytes()));
                callback.success(obj, response);
        }

        // ...
}
```
