package com.javarunner.lesson3.presentation;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void openFileChooser();
    @StateStrategyType(SkipStrategy.class)
    void showConvertingSuccessfulMessage();
    @StateStrategyType(SkipStrategy.class)
    void showConvertingUnsuccessfulMessage();

    void showAbortDialog();
    void closeAbortDialog();
    void showConvertedImage(File imageFile);
}
