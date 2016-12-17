package com.example.dmitry.a1c_client.domain;


import com.example.dmitry.a1c_client.domain.entity.Document;

import java.util.List;

import rx.Single;

public interface DocumentRepository {
    Single<List<Document>> getDocuments();
}
