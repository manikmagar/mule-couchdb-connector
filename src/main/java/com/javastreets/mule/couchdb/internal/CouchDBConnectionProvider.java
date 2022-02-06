package com.javastreets.mule.couchdb.internal;

import org.mule.runtime.api.connection.*;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class CouchDBConnectionProvider implements CachedConnectionProvider<CouchDBConnection>, Startable, Stoppable {

  private final Logger LOGGER = LoggerFactory.getLogger(CouchDBConnectionProvider.class);

  @Inject
  private HttpService httpService;

  @Parameter
  @DisplayName("CouchDB Server URL")
  @Example("http://localhost:5984")
  private String url;

  @Parameter
  private String username;

  @RefName
  private String configName;


  @Parameter
  @Password
  private String password;
  private HttpClient httpClient;

  @Override
  public CouchDBConnection connect() throws ConnectionException {
    return new CouchDBConnection(url, username, password, httpClient);
  }

  @Override
  public void disconnect(CouchDBConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(CouchDBConnection connection) {
    return ConnectionValidationResult.success();
  }


  @Override
  public void start() throws MuleException {
    httpClient = httpService.getClientFactory()
            .create(new HttpClientConfiguration.Builder()
                    .setName(configName)
                    .build());
    httpClient.start();
  }

  @Override
  public void stop() throws MuleException {
    if (httpClient != null) {
      httpClient.stop();
    }
  }

}
