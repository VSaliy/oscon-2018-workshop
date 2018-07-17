package com.oscon2018.tutorials.index;

import com.oscon2018.tutorials.models.IndexerResponse;
import com.oscon2018.tutorials.models.QueryRequest;
import com.oscon2018.tutorials.models.QueryResponse;

import java.io.InputStream;

public interface IndexService {
    IndexerResponse indexData(String indexName, InputStream csv);
    QueryResponse query(String indexName, QueryRequest request);
}
