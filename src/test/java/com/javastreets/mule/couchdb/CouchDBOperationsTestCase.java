package com.javastreets.mule.couchdb;

import com.github.tomakehurst.wiremock.client.BasicCredentials;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class CouchDBOperationsTestCase extends MuleArtifactFunctionalTestCase {

  @Rule
  public DynamicPort dynamicPort = new DynamicPort("http.port");

  @Rule
  public WireMockRule couchdbApiMock = new WireMockRule(options().port(dynamicPort.getNumber()));


  /**
   * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
   */
  @Override
  protected String getConfigFile() {
    return "test-mule-config.xml";
  }

  @Test
  public void executeGetDatabase() throws Exception {

    couchdbApiMock.stubFor(get(urlEqualTo("/test"))
            .willReturn(okJson("{\"db_name\":\"test\"}")));
    String database = (String) flowRunner("getDatabase").run().getMessage().getPayload().getValue();
    assertThat(database).isEqualTo("{\"db_name\":\"test\"}");
    verify(getRequestedFor(urlEqualTo("/test"))
            .withBasicAuth(new BasicCredentials("admin", "password")));
  }

}
