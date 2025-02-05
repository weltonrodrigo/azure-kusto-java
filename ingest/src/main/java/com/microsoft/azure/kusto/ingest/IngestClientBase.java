package com.microsoft.azure.kusto.ingest;

import com.microsoft.azure.kusto.ingest.source.CompressionType;

public abstract class IngestClientBase {
    static final String INGEST_PREFIX = "ingest-";
    static final String PROTOCOL_SUFFIX = "://";

    String connectionDataSource;

    static boolean shouldCompress(CompressionType sourceCompressionType, IngestionProperties.DataFormat dataFormat) {
        return (sourceCompressionType == null) && (dataFormat == null || dataFormat.isCompressible());
    }

    static String getIngestionEndpoint(String clusterUrl) {
        if (clusterUrl.contains(INGEST_PREFIX)) {
            return clusterUrl;
        } else {
            return clusterUrl.replaceFirst(PROTOCOL_SUFFIX, PROTOCOL_SUFFIX + INGEST_PREFIX);
        }
    }

    static String getQueryEndpoint(String clusterUrl) {
        if (clusterUrl.contains(INGEST_PREFIX)) {
            return clusterUrl.replaceFirst(INGEST_PREFIX, "");
        } else {
            return clusterUrl;
        }
    }

}
