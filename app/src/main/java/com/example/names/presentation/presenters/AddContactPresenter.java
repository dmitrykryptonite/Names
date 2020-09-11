package com.example.names.presentation.presenters;

import com.example.names.domain.AddContactInteractorImpl;
import com.example.names.presentation.view.AddContactView;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class AddContactPresenter extends MvpPresenter<AddContactView> {
    private AddContactInteractorImpl addNameInteractorImpl = new AddContactInteractorImpl();
    private Disposable disposableSaveContact;

    public void wrongLengthEditTextName() {
        getViewState().showWarningMassage("Sorry, length name must be 1-40 symbols");
    }

    public void wrongLengthEditTextCallNumber() {
        getViewState().showWarningMassage("Sorry, length call number must be 1-13 symbols");
    }

    public void onRootViewClicked() {
        getViewState().rootViewIsFocused();
        getViewState().hideKeyboard();
    }

    public void onBtnSubmitClicked(String name, String callNumber) {
        if (name.isEmpty() || callNumber.isEmpty())
            getViewState().showWarningMassage("The lines must not be empty");
        else if (name.length() >= 41)
            getViewState().showWarningMassage("Sorry, length name must be 1-40 symbols");
        else if (callNumber.length() >= 14)
            getViewState().showWarningMassage("Sorry, length call number must be 1-13 symbols");
        else {
            Completable saveContact = addNameInteractorImpl.saveContact(name, callNumber);
            disposableSaveContact = saveContact.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getViewState().setTextEditTextName("");
                        getViewState().setTextEditTextCallNumber("");
                        getViewState().hideKeyboard();
                        getViewState().showSuccessMassage("Contact saved");
                        getViewState().rootViewIsFocused();
                    }, throwable -> getViewState().showErrorMassage(throwable.getMessage()));
        }
    }

    public void onEditTextNameFocusChanged(boolean hasFocus) {
        if (hasFocus)
            getViewState().showKeyboardForEtName();
        else {
            getViewState().rootViewIsFocused();
            getViewState().hideKeyboard();
        }
    }

    public void onEditTextCallNumberFocusChanged(boolean hasFocus) {
        if (hasFocus)
            getViewState().showKeyboardForEtCallNumber();
        else {
            getViewState().rootViewIsFocused();
            getViewState().hideKeyboard();
        }
    }

    public void onPauseView() {
        getViewState().rootViewIsFocused();
        getViewState().hideKeyboard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableSaveContact != null && disposableSaveContact.isDisposed())
            disposableSaveContact.dispose();
    }
}
