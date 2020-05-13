# Wikipedia Event Stream Replicators

This is a collection of server-sent-event (SSE) replicators that ingest real-time event streams from Wikimedia's event platform APIs into Kafka topics. A Spring Boot application is used to consume Wikipedia's HTTP-based event streams using a reactive SSE client with retries. The contents of each event's payload from a Wikipedia subscription is pushed as a message into a Kafka topic. With the SSE events stored in Kafka, Apache Pinot can be used to ingest events from Kafka using a real-time table and schema. You can then query Pinot using SQL as data from Wikipedia is continuously fed into the cluster.

![Superset Pinot Charts](https://i.imgur.com/py4mllo.png)

## Build

The best way to use this example is to deploy it to a Kubernetes cluster that has Kafka installed. Start by compiling the application with Docker running.

    mvn clean install -DskipTests

This will build a docker image. You can modify the DockerHub user that this image will be associated with in the Maven BOM of the parent project, located here:

https://github.com/kbastani/pinot-wikipedia-event-stream/blob/c77b404b9dedf120f09fc1abb59d0033f78022a5/pom.xml#L25

## Run

You can run the example using Docker compose using the supplied `docker-compose.yml` file. This will ingest events from Wikipedia to a new Kafka cluster.

    docker-compose up

This will start Kafka, Zookeeper, Pinot and the `wiki-event-stream` application. After everything is up and running and you start to see events being output to the log, you can create the Pinot table for the Wikipedia change events.

First, you need to copy the JSON schema and table definition to the Pinot container. Run the following command in another terminal.

    docker cp ./pinot pinot-wikipedia-event-stream_pinot_1:.

After the JSON files are copied to the Pinot container, you can create the schema and table.

    docker exec pinot-wikipedia-event-stream_pinot_1 bin/pinot-admin.sh AddTable \ 
        -controllerHost localhost \
        -tableConfigFile /pinot/wiki-table-definition.json \
        -schemaFile /pinot/wiki-schema-definition.json \
        -exec

After creating the schema and table, you can start querying the real-time change feed from Wikipedia.

    docker exec pinot-wikipedia-event-stream_pinot_1 bin/pinot-admin.sh PostQuery \
      -brokerHost localhost \
      -brokerPort 8000 \
      -queryType pql \
      -query "SELECT title, COUNT(*) from wikiRecentChangeTable GROUP BY title TOP 10"

You can run these queries directly in the Pinot data browser.

    http://localhost:9000/query

Copy and paste the following query in the query console and run it.

    SELECT category, COUNT(*)
    FROM wikiRecentChangeTable
    GROUP BY category
    ORDER BY COUNT(*) DESC
    LIMIT 100
   
You should see something similar to the following result.

    category	count(*)
    Category:All articles with unsourced statements	555
    Category:Articles with hCards	421
    Category:Articles with short description	1289
    Category:Living people	515
    Category:Noindexed pages	390
    Category:Webarchive template wayback links	392
    Category:Wikipedia articles with GND identifiers	346
    Category:Wikipedia articles with LCCN identifiers	417
    Category:Wikipedia articles with VIAF identifiers	457
    Category:Wikipedia articles with WorldCat identifiers	415

Now try searching for virus related articles. Make sure PQL syntax is checked before running this query.

    SELECT category, COUNT(*) as rank FROM wikiRecentChangeTable WHERE regexp_like(category, 'virus')
    GROUP BY category
    ORDER BY rank DESC
    TOP 10

You should see something similar to the following output.

    category	count_star
    Category:Aphthoviruses	1
    Category:B-Class virus articles	2
    Category:C-Class virus articles	2
    Category:Low-importance virus articles	4
    Category:NA-importance virus articles	3
    Category:Project-Class virus articles	1
    Category:Template-Class virus articles	2
    Category:Virus stubs	1
    Category:WikiProject Viruses articles	7

## Running in Kubernetes

Please visit the Apache Pinot documentation to learn how to easily setup a quick start cluster that has Kafka, Zookeeper, Pinot, and Superset.

[Running Pinot in Kubernetes](https://docs.pinot.apache.org/getting-started/kubernetes-quickstart)

By following this guide you'll have a functioning Kubernetes cluster that you can deploy the `wiki-event-stream` replicator to. The replicator will continuously subscribe to events published by the Wikimedia event stream APIs, and will durably retry with backoffs in the case of a failure.
