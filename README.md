# 1. Android - How create a mock server with Retrofit 

The meta of this project is to show you how build a mock server with retrofit.

When you are working on your android app could happen you need to test you server call, but how can you do that if the server apis aren't ready? 
The answer is to create a mock server as following.

Suppose you have built the following Api class, 
```java
public class Api {

    public static final String URL_ENDPOINT = "https://api.stackexchange.com";

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
