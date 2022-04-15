package com.broadfactor.desafio.resources.receitaws.client;

import com.broadfactor.desafio.resources.receitaws.service.ReceitawsService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class ReceitawsClient {

    @Value("${API_RECEITAWS_URL}")
    private String url;
    private Retrofit retrofit;


    public ReceitawsService getService() {
        if (retrofit == null) {
            createBuilder(url);
        }
        return retrofit.create(ReceitawsService.class);
    }

    private void createBuilder(String url) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
