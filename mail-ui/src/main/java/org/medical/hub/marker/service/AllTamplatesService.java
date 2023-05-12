package org.medical.hub.marker.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class AllTamplatesService {
    private String baseUrl = "http://localhost:8080/template";

    public String getQueryApi() {
        String urlForTemplateList = baseUrl + "/template-list";
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(urlForTemplateList);

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

    public String findTemplateByTag(String tag) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            uriBuilder.addParameter("tag", tag);


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

    public String deleteTemplateByTag(String tag) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            String urlForDelete = baseUrl + "/delete";
            URIBuilder uriBuilder = new URIBuilder(urlForDelete);
            uriBuilder.addParameter("tag", tag);


            String requestURI = uriBuilder.build().toString();

//            HttpGet getRequest = new HttpGet(requestURI);
            HttpDelete deleteRequest = new HttpDelete(requestURI);

            CloseableHttpResponse response = client.execute(deleteRequest);

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
