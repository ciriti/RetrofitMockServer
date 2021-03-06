# 1. Android - How emulate fake server's responses with mock server using Retrofit's MockRestAdapter 

The purpose of this article is ONLY to show you how create a mock server with retrofit and emulate its responses, if you need also an example of architecture you could take a glance to my article about DAGGER 2. Anyway this will be the start point for all other future project.  

When you are working on your android app could happen you need to test you server call but, how can you do that if the server apis aren't ready? 
The answer is to create a mock server as following.

Suppose you have built the following Api class, 
```java
public class Api {

    public static final String URL_ENDPOINT = "https://api.stackexchange.com";
    
    //...

    RestAdapter restAdapter;
    ApiService service;
    ApiService mockService;
    
    //..

    private Api(){
        this.restAdapter = getRestAdapter(getClient(), getEndpoint());
        this.service = this.restAdapter.create(ApiService.class);
        this.mockService = MockRestAdapter.from(this.restAdapter).create(ApiService.class, new MockService());
    }

    //..

    public ApiService stackExchangeService(boolean hasUseMock){
        if(hasUseMock)
            return mockService;
        return service;
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

        public static final String DATA = "FAKE JSON";

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

        // ...
}
```

Now all it's ready to be used. In your activity or where you want, to use your mock server is sufficent write the standard code to use Retrofit:
1. Async way
```java
 Api.getInstance().stackExchangeService(isMock).getUsers(10, new Callback<RespBean>() {
            @Override
            public void success(RespBean respBean, Response response) {
                //..
            }

            @Override
            public void failure(RetrofitError error) {
                //..
            }
        });
```
2. sync way
```java
    Api.getInstance().stackExchangeService(isMock).getUsers(10)
```
You can see all code in the related src.
