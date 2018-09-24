package com.javarunner.lesson3.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.javarunner.lesson3.R;
import com.javarunner.lesson3.presentation.MainPresenter;
import com.javarunner.lesson3.presentation.MainView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final int REQUEST_CODE = 1001;
    private CompositeDisposable disposables;
    private Disposable abortDialog;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(AndroidSchedulers.mainThread());
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposables = new CompositeDisposable();
        disposables.add(RxView.clicks(findViewById(R.id.button)).subscribe(o -> presenter.onButtonClick()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || requestCode != REQUEST_CODE) return;
        if (data == null || data.getData() == null) return;

        String uri = data.getData().toString();
        presenter.onConvertImage(uri);
    }

    @Override
    public void showConvertedImage(File imageFile) {
        Picasso.get()
                .load(imageFile)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .centerCrop()
                .into((ImageView) findViewById(R.id.image_view));
    }

    @Override
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.file_chooser_title)), REQUEST_CODE);
    }

    @Override
    public void showConvertingSuccessfulMessage() {
        Toast.makeText(MainActivity.this, R.string.successful, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConvertingUnsuccessfulMessage() {
        Toast.makeText(MainActivity.this, R.string.unsuccessful, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAbortDialog() {
        abortDialog = createAbortDialog().subscribe(integer -> presenter.onAbortConversion());
    }

    private Maybe<Integer> createAbortDialog() {
        return Maybe.create(emitter -> {
            Dialog abortDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.abort_title)
                    .setMessage(R.string.abort_message)
                    .setPositiveButton(R.string.abort_button, (d, w) -> emitter.onSuccess(w))
                    .create();

            abortDialog.show();
            emitter.setCancellable(() -> {
                if (abortDialog.isShowing()) {
                    abortDialog.dismiss();
                }
            });
        });
    }

    @Override
    public void closeAbortDialog() {
        if (abortDialog == null) return;
        abortDialog.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
