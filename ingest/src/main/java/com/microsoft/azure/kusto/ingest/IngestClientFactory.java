// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.

package com.microsoft.azure.kusto.ingest;

import com.microsoft.azure.kusto.data.HttpClientProperties;
import com.microsoft.azure.kusto.data.auth.ConnectionStringBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;

public class IngestClientFactory {
    private IngestClientFactory() {
        // Hide the default constructor, as this is a factory with static methods
    }

    /**
     * Creates a new queued ingest client, with default http client properties.
     * @param csb connection string builder for the data management endpoint
     * @return a new queued ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static QueuedIngestClient createClient(ConnectionStringBuilder csb) throws URISyntaxException {
        return createClient(csb, (HttpClientProperties) null);
    }

    /**
     * Creates a new queued ingest client.
     * @param csb connection string builder for the data management endpoint
     * @param properties additional properties to configure the http client
     * @return a new queued ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static QueuedIngestClient createClient(ConnectionStringBuilder csb, @Nullable HttpClientProperties properties) throws URISyntaxException {
        return new QueuedIngestClientImpl(csb, properties);
    }

    /**
     * Creates a new queued ingest client.
     * @param csb connection string builder for the data management endpoint
     * @param client HTTP Client to use for service and storage calls
     * @return a new queued ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static QueuedIngestClient createClient(ConnectionStringBuilder csb, @Nullable CloseableHttpClient client) throws URISyntaxException {
        return new QueuedIngestClientImpl(csb, client);
    }

    /**
     * Creates a new streaming ingest client, with default http client properties.
     * @param csb connection string builder for the engine endpoint
     * @return a new streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static StreamingIngestClient createStreamingIngestClient(ConnectionStringBuilder csb) throws URISyntaxException {
        return createStreamingIngestClient(csb, null);
    }

    /**
     * Creates a new streaming ingest client.
     * @param csb connection string builder for the engine endpoint
     * @param properties additional properties to configure the http client
     * @return a new streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static StreamingIngestClient createStreamingIngestClient(ConnectionStringBuilder csb, @Nullable HttpClientProperties properties)
            throws URISyntaxException {
        return new StreamingIngestClient(csb, properties);
    }

    /**
     * Creates a new managed streaming ingest client, with default http client properties.
     * This method should only be used for advanced cases. If your endpoints are standard, or you do not know, use {@link #createManagedStreamingIngestClient(ConnectionStringBuilder)} instead.
     * @param ingestionEndpointConnectionStringBuilder connection string builder for the data management endpoint
     * @param queryEndpointConnectionStringBuilder connection string builder for the engine endpoint
     * @return a new managed streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClient(ConnectionStringBuilder ingestionEndpointConnectionStringBuilder,
            ConnectionStringBuilder queryEndpointConnectionStringBuilder)
            throws URISyntaxException {
        return createManagedStreamingIngestClient(ingestionEndpointConnectionStringBuilder, queryEndpointConnectionStringBuilder, null);
    }

    /**
     * Creates a new managed streaming ingest client.
     * This method should only be used for advanced cases. If your endpoints are standard, or you do not know, use
     * {@link #createManagedStreamingIngestClient(ConnectionStringBuilder, HttpClientProperties)} instead.
     * @param ingestionEndpointConnectionStringBuilder connection string builder for the data management endpoint
     * @param queryEndpointConnectionStringBuilder connection string builder for the engine endpoint
     * @param properties additional properties to configure the http client
     * @return a new managed streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClient(ConnectionStringBuilder ingestionEndpointConnectionStringBuilder,
            ConnectionStringBuilder queryEndpointConnectionStringBuilder, @Nullable HttpClientProperties properties)
            throws URISyntaxException {
        return new ManagedStreamingIngestClient(ingestionEndpointConnectionStringBuilder, queryEndpointConnectionStringBuilder, properties);
    }

    /**
     * Creates a new managed streaming ingest client, with default http client properties.
     * This method supports both an ingestion and query endpoint, and deduces the other endpoint from the given one.
     * @param connectionStringBuilder connection string builder for the client
     * @return a new managed streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClient(ConnectionStringBuilder connectionStringBuilder)
            throws URISyntaxException {
        return createManagedStreamingIngestClient(connectionStringBuilder, (HttpClientProperties) null);
    }

    /**
     * Creates a new managed streaming ingest client.
     * This method supports both an ingestion and query endpoint, and deduces the other endpoint from the given one.
     * @param connectionStringBuilder connection string builder for the client
     * @param properties additional properties to configure the http client
     * @return a new managed streaming ingest client
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClient(ConnectionStringBuilder connectionStringBuilder,
            @Nullable HttpClientProperties properties)
            throws URISyntaxException {
        return new ManagedStreamingIngestClient(connectionStringBuilder, properties);
    }

    /**
     * @deprecated - Ingest clients now automatically deduce the endpoint, use {@link #createManagedStreamingIngestClient(ConnectionStringBuilder)} instead.
     * Creates a new ManagedStreamingIngestClient from an engine connection string, with default http client properties.
     * This method infers the DM connection string from the engine connection string.
     * For advanced usage, use {@link ManagedStreamingIngestClient#ManagedStreamingIngestClient(ConnectionStringBuilder, ConnectionStringBuilder)}
     * @param engineConnectionStringBuilder engine connection string
     * @return a new ManagedStreamingIngestClient
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClientFromEngineCsb(ConnectionStringBuilder engineConnectionStringBuilder)
            throws URISyntaxException {
        return createManagedStreamingIngestClientFromEngineCsb(engineConnectionStringBuilder, null);
    }

    /**
     * @deprecated - Ingest clients now automatically deduce the endpoint, use {@link #createManagedStreamingIngestClient(ConnectionStringBuilder, HttpClientProperties)} instead.
     * Creates a new ManagedStreamingIngestClient from an engine connection string.
     * This method infers the DM connection string from the engine connection string.
     * For advanced usage, use {@link ManagedStreamingIngestClient#ManagedStreamingIngestClient(ConnectionStringBuilder, ConnectionStringBuilder)}
     * @param engineConnectionStringBuilder engine connection string
     * @param properties additional properties to configure the http client
     * @return a new ManagedStreamingIngestClient
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClientFromEngineCsb(ConnectionStringBuilder engineConnectionStringBuilder,
            @Nullable HttpClientProperties properties)
            throws URISyntaxException {
        return ManagedStreamingIngestClient.fromEngineConnectionString(engineConnectionStringBuilder, properties);
    }

    /**
     * @deprecated - Ingest clients now automatically deduce the endpoint, use {@link #createManagedStreamingIngestClient(ConnectionStringBuilder)} instead.
     * Creates a new ManagedStreamingIngestClient from a DM connection string, with default http client properties.
     * This method infers the engine connection string from the DM connection string.
     * For advanced usage, use {@link ManagedStreamingIngestClient#ManagedStreamingIngestClient(ConnectionStringBuilder, ConnectionStringBuilder)}
     * @param dmConnectionStringBuilder dm connection stringbuilder
     * @return a new ManagedStreamingIngestClient
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClientFromDmCsb(ConnectionStringBuilder dmConnectionStringBuilder)
            throws URISyntaxException {
        return createManagedStreamingIngestClientFromDmCsb(dmConnectionStringBuilder, (HttpClientProperties) null);
    }

    /**
     * @deprecated - Ingest clients now automatically deduce the endpoint, use {@link #createManagedStreamingIngestClient(ConnectionStringBuilder, HttpClientProperties)} instead.
     * Creates a new ManagedStreamingIngestClient from a DM connection string.
     * This method infers the engine connection string from the DM connection string.
     * For advanced usage, use {@link ManagedStreamingIngestClient#ManagedStreamingIngestClient(ConnectionStringBuilder, ConnectionStringBuilder)}
     * @param dmConnectionStringBuilder dm connection stringbuilder
     * @param properties additional properties to configure the http client
     * @return a new ManagedStreamingIngestClient
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClientFromDmCsb(ConnectionStringBuilder dmConnectionStringBuilder,
            @Nullable HttpClientProperties properties)
            throws URISyntaxException {
        return ManagedStreamingIngestClient.fromDmConnectionString(dmConnectionStringBuilder, properties);
    }

    /**
     * Creates a new ManagedStreamingIngestClient from a DM connection string.
     * This method infers the engine connection string from the DM connection string.
     * For advanced usage, use {@link ManagedStreamingIngestClient#ManagedStreamingIngestClient(ConnectionStringBuilder, ConnectionStringBuilder)}
     * @param dmConnectionStringBuilder dm connection string builder
     * @param httpClient HTTP Client to use for service and storage calls
     * @return a new ManagedStreamingIngestClient
     * @throws URISyntaxException if the connection string is invalid
     */
    public static ManagedStreamingIngestClient createManagedStreamingIngestClientFromDmCsb(ConnectionStringBuilder dmConnectionStringBuilder,
            @Nullable CloseableHttpClient httpClient)
            throws URISyntaxException {
        return ManagedStreamingIngestClient.fromDmConnectionString(dmConnectionStringBuilder, httpClient);
    }
}
