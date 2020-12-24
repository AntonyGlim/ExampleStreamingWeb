package example.streaming.web.client.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import example.streaming.web.common.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This example demonstrate an asynchronous HTTP request / response exchange
 * with a full content streaming.
 */
@Slf4j
public class AsyncClientHttpExchangeStreaming {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8285/esw/item/stream/get/all");
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    try (JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream))) {
                        Gson gsonBuilder = new GsonBuilder().create();
                        jsonReader.beginArray(); //start of json array
                        int recordsCount = 0;
                        while (jsonReader.hasNext()) { //next json array element
                            Item item = gsonBuilder.fromJson(jsonReader, Item.class);
                            logAndDoSomethingImportant(item);
                            recordsCount++;
                        }
                        jsonReader.endArray();
                        log.info("Total items found : {}", recordsCount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void logAndDoSomethingImportant(Item item) {
        log.info("{}", item);
        //and do something more important
    }

}