package com.javarunner.lesson3.presentation;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {
    void openFileChooser();

    void showConvertingSuccessfulMessage();

    void showConvertingUnsuccessfulMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showConvertedImage(File imageFile);
}
