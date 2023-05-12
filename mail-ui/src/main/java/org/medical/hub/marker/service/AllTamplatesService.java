package org.medical.hub.marker.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllTamplatesService {
    private String baseUrl = "http://localhost:8080/template/template-list";

    public String getQueryApi() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(baseUrl);

            String requestURI = uriBuilder.build().toString();

            HttpGet getRequest = new HttpGet(requestURI);

            CloseableHttpResponse response = client.execute(getRequest);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity httpEntity = response.getEntity();
                String responseBody = EntityUtils.toString(httpEntity);
return responseBody;
            }

        } catch (Exception ex) {
        }
        return null;
    }
}
