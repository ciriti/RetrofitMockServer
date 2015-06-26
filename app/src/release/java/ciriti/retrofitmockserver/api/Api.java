package ciriti.retrofitmockserver.api;

import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by carmelo.iriti on 26/06/2015.
 */
public class Api {

    public static final String URL_ENDPOINT = "https://api.stackexchange.com";

    protected RestAdapter getAdapter(Endpoint endpoint, Client client){
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .build();
    }

    public ApiService stackExchangeService(boolean hasUseMock){
        return getAdapter(getEndpoint(), getClient()).create(ApiService.class);
    }

    protected Endpoint getEndpoint(){
        return Endpoints.newFixedEndpoint(URL_ENDPOINT);
    }

    protected Client getClient(){
        return new OkClient();
    }

}
