package com.javastreets.mule.couchdb.internal;

import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.runtime.operation.Result;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class CouchDBOperations {

  @MediaType(value = MediaType.APPLICATION_JSON)
  public Result<String, Void> getDatabase(String databaseName, @Connection CouchDBConnection connection) throws Exception {
    return Result.<String, Void>builder()
            .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
            .output(connection.getDatabase(databaseName))
            .build();
  }

}
