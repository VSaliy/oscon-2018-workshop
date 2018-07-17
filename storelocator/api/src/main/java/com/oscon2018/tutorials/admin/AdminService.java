package com.oscon2018.tutorials.admin;


import com.oscon2018.tutorials.models.Index;

public interface AdminService {
    void createIndex(Index index);
    void deleteIndex(String indexName);
}
