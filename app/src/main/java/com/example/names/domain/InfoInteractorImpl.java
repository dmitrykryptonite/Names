package com.example.names.domain;

import com.example.names.data.repositories.MainRepositoryImpl;
import com.example.names.domain.entities.Contact;

import io.reactivex.Completable;
import io.reactivex.Single;

public class InfoInteractorImpl implements InfoInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();

    @Override
    public Single<Contact> getInfoContact() {
        return mainRepositoryImpl.getInfoContact();
    }

    @Override
    public Completable editContact(int id, String name, String callNumber) {
        return mainRepositoryImpl.editContact(id, name, callNumber);
    }
}
