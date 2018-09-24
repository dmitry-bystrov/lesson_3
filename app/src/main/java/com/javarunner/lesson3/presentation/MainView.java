package com.javarunner.lesson3.presentation;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
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

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showAbortDialog();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void closeAbortDialog();

    void showConvertedImage(File imageFile);
}
