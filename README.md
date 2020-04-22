# Wikipedia Event Stream Replicators

This is a collection of server-sent-event (SSE) replicators that ingest real-time event streams from Wikimedia's event platform APIs into Kafka topics. A Spring Boot application is used to consume Wikipedia's HTTP-based event streams using a reactive SSE client with retries. The contents of each event's payload from a Wikipedia subscription is pushed as a message into a Kafka topic. With the SSE events stored in Kafka, Apache Pinot can be used to ingest events from Kafka using a real-time table and schema. You can then query Pinot using SQL as data from Wikipedia is continuously fed into the cluster.

## Usage

The best way to use this example is to deploy it to a Kubernetes cluster that has Kafka installed. Start by compiling the application with Docker running.

    mvn clean install -DskipTests

This will build a docker image. You can modify the DockerHub user that this image will be associated with in the Maven BOM of the parent project, located here:

https://github.com/kbastani/pinot-wikipedia-event-stream/blob/c77b404b9dedf120f09fc1abb59d0033f78022a5/pom.xml#L25

## Running in Docker

You can run the example using Docker compose using the supplied `docker-compose.yml` file. This will ingest events from Wikipedia to a new Kafka cluster.

    docker-compose up

This will start Kafka and Zookeeper and then the `wiki-event-stream` application.

## Running in Kubernetes

Please visit the Apache Pinot documentation to learn how to easily setup a quick start cluster that has Kafka, Zookeeper, Pinot, and Superset.

[Running Pinot in Kubernetes](https://docs.pinot.apache.org/getting-started/kubernetes-quickstart)

By following this guide you'll have a functioning Kubernetes cluster that you can deploy the `wiki-event-stream` replicator to. The replicator will continuously subscribe to events published by the Wikimedia event stream APIs, and will durably retry with backoffs in the case of a failure.
