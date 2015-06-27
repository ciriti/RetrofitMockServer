package ciriti.retrofitmockserver.api;

import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by carmelo.iriti on 26/06/2015.
 */
public class Api {

    public static final String URL_ENDPOINT = "https://api.stackexchange.com";

    /**
     * Singleton - object's creation at the same time of the class's initialization
     */
    private final static Api INSTANCE = new Api();

    RestAdapter restAdapter;
    ApiService service;
    ApiService mockService;

    private Api(){
        this.restAdapter = getRestAdapter(getClient(), getEndpoint());
        this.service = this.restAdapter.create(ApiService.class);
        this.mockService = MockRestAdapter.from(this.restAdapter).create(ApiService.class, new MockService());
    }

    public static Api getInstance(){
        return INSTANCE;
    }

    public ApiService stackExchangeService(boolean hasUseMock){
        if(hasUseMock)
            return mockService;
        return service;
    }

    Endpoint getEndpoint(){
        return Endpoints.newFixedEndpoint(URL_ENDPOINT);
    }

    Client getClient(){
        return new OkClient();
    }

    RestAdapter getRestAdapter(Client client, Endpoint endpoint){
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .build();
    }

}
