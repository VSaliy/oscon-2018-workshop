package com.oscon2018.tutorials.admin;

import com.oscon2018.tutorials.domain.StoreRepository;
import com.oscon2018.tutorials.models.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StoreRepository repository;

    @Override
    public void createIndex(Index index) {
        // Put business logic here

        repository.createIndex(index.getName());
    }

    @Override
    public void deleteIndex(String indexName) {
        // Put business logic/rules here

        repository.deleteIndex(indexName);
    }
}
