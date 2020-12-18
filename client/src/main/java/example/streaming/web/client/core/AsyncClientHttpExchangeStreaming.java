package example.streaming.web.client.core;
/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import example.streaming.web.common.model.Item;
import example.streaming.web.common.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 * This example demonstrates an asynchronous HTTP request / response exchange with
 * a full content streaming.
 */
@Slf4j
public class AsyncClientHttpExchangeStreaming {

    public static void main(String[] args) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://192.168.200.58:8285/esw/item/stream/get/all");
        try (CloseableHttpResponse response1 = client.execute(httpGet)) {
            final HttpEntity entity = response1.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
//                    JsonFactory factory = new JsonFactory();
//                    JsonParser jParser = factory.createParser(inputStream);
//                    while (jParser.nextToken() != JsonToken.END_ARRAY) {
//                        String name = jParser.getCurrentName();
//                        String text = jParser.getValueAsString();
//                        log.info("name: {}, text: {}", name, text);
//                    }
//                    log.info("END");
//                    jParser.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = br.readLine()) != null) {
                        JsonFactory factory = new JsonFactory();
                        JsonParser jParser = factory.createParser(line);
                        while (jParser.nextToken() != JsonToken.END_OBJECT) {
                            String name = jParser.getCurrentName();
                            String text = jParser.getValueAsString();
                            log.info("name: {}, text: {}", name, text);
                        }
                        log.info("END");
                        log.info(line);
                    }
                }
            }
        }
    }



//    public static void main(final String[] args) throws Exception {
//        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
//        try {
//            httpclient.start();
//
//            Future<Boolean> future = httpclient.execute(
//                    HttpAsyncMethods.createGet("http://192.168.200.58:8285/esw/item/stream/get/all"),
//                    new MyResponseConsumer(),
//                    null
//            );
//            Boolean result = future.get();
//
//            if (result != null && result) {
//                log.info("Request successfully executed");
//            } else {
//                log.info("Request failed");
//            }
//            log.info("Shutting down");
//        } finally {
//            httpclient.close();
//        }
//        log.info("Done");
//    }
//
//
//    static class MyResponseConsumer extends AsyncCharConsumer<Boolean> {
//
//        final ItemRepository itemRepository = new ItemRepository();
//
//
//        @Override
//        protected void onResponseReceived(final HttpResponse response) {
//            log.info("onResponseReceived");
//        }
//
//        @Override
//        protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
//            StringBuilder sb = new StringBuilder();
//            while (buf.hasRemaining()) {
//                sb.append(buf.get());
//            }
//            log.info("size: {}", itemRepository.getItems().size());
//            log.info("{}", sb.toString());
//            Item item = JsonMapper.deserializeObject(sb.toString(), Item.class);
//            itemRepository.getItems().put(item.getId(), item);
//        }
//
//        @Override
//        protected void releaseResources() {
//        }
//
//        @Override
//        protected Boolean buildResult(final HttpContext context) {
//            return Boolean.TRUE;
//        }
//
//    }

}