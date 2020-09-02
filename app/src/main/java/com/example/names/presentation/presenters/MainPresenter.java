package com.example.names.presentation.presenters;

import com.example.names.domain.MainInteractorImpl;
import com.example.names.presentation.view.MainView;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private MainInteractorImpl mainInteractorImpl = new MainInteractorImpl();
    private Disposable disposableDeleteAllNames;

    public void onBtnDeleteClicked() {
        Completable deleteAllNames = mainInteractorImpl.deleteAllNames();
        disposableDeleteAllNames = deleteAllNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        getViewState().showSuccessMassage("All names deleted");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getViewState().showErrorMassage(throwable.getMessage());
                    }
                });
    }

    public void releasePresenter() {
        if (disposableDeleteAllNames != null && disposableDeleteAllNames.isDisposed())
            disposableDeleteAllNames.dispose();
    }
}
