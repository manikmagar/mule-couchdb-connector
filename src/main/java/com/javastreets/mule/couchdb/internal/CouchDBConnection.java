package com.javastreets.mule.couchdb.internal;


import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.client.auth.HttpAuthentication;
import org.mule.runtime.http.api.domain.HttpProtocol;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class CouchDBConnection {

  private final String url;
  private final String username;
  private final String password;
  private final HttpClient httpClient;

  public CouchDBConnection(String url, String username, String password, HttpClient httpClient) {
    this.url = url;
    this.username = username;
    this.password = password;
    this.httpClient = httpClient;
  }

  public String getDatabase(String databaseName) throws IOException, TimeoutException {
    HttpRequest request = HttpRequest.builder()
            .uri(url.concat("/").concat(databaseName))
            .method(HttpConstants.Method.GET)
            .protocol(HttpProtocol.HTTP_1_0).build();
    HttpRequestOptions httpRequestOptions = HttpRequestOptions.builder().authentication(HttpAuthentication.basic(username, password).build()).build();

    HttpResponse response = httpClient.send(request,httpRequestOptions);
    return IOUtils.toString(response.getEntity().getContent());
  }


  public void invalidate() {
    if(httpClient != null) {
      httpClient.stop();
    }
  }
}
