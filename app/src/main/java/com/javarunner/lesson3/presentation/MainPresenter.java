package com.javarunner.lesson3.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.javarunner.lesson3.model.Image;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private Disposable conversion;
    private Image image;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.ioScheduler = Schedulers.io();
        this.image = new Image();
    }

    public void onButtonClick() {
        getViewState().openFileChooser();
    }

    public void onConvertImage(String fileUri) {
        image.convert(fileUri)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        conversion = d;
                        getViewState().showAbortDialog();
                    }

                    @Override
                    public void onComplete() {
                        getViewState().closeAbortDialog();
                        getViewState().showConvertingSuccessfulMessage();
                        getViewState().showConvertedImage(image.getFile());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().closeAbortDialog();
                        getViewState().showConvertingUnsuccessfulMessage();
                    }
                });
    }

    public void onAbortConversion() {
        disposeConversion();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeConversion();
    }

    private void disposeConversion() {
        if (conversion == null) return;
        conversion.dispose();
    }
}
