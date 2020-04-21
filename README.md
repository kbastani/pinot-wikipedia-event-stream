# Wikipedia Event Stream Replicators

This is a collection of server-sent-event (SSE) replicators that ingest real-time event streams from Wikimedia's event platform APIs into Kafka topics. A Spring Boot application is used to consume Wikipedia's HTTP-based event streams using a reactive SSE client with retries. The contents of each event's payload from a Wikipedia subscription is pushed as a message into a Kafka topic. With the SSE events stored in Kafka, Apache Pinot can be used to ingest events from Kafka using a real-time table and schema. You can then query Pinot using SQL as data from Wikipedia is continuously fed into the cluster.

# Overview








