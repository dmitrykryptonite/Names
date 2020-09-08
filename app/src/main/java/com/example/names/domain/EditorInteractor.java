package com.example.names.domain;

import com.example.names.domain.entities.Name;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface EditorInteractor {
    Single<Name> getEditName();

    Completable editName(int id, String name);
}
