# CouchDB Connector
A CouchDB Mule Connector to integrate CouchDB with Mule.

This live demo was streamed on Mule Community Twitch channel. You can see recording [here](https://www.twitch.tv/videos/1287212021).

To run CouchDB in docker - https://hub.docker.com/_/couchdb/

- Run docker container - `docker run -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=password -p 5984:5984 -d couchdb`
- Login to http://127.0.0.1:5984/_utils/
- Create new Database 'test'


Add this dependency to your application pom.xml

```
<groupId>com.javastreets</groupId>
<artifactId>mule-couchdb-connector</artifactId>
<version>0.0.12</version>
<classifier>mule-plugin</classifier>
```
