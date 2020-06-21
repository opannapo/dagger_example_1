package com.opannapo.daggerexample_1.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.opannapo.daggerexample_1.R;
import com.opannapo.daggerexample_1.components.DaggerKalkulatorComponent;
import com.opannapo.daggerexample_1.dependencies.Kalkulator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaggerSinCosTanActivity extends AppCompatActivity {
    @Inject
    Kalkulator kalkulator;

    @BindView(R.id.textView)
    TextView textView;

    final String PAGE = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satu);
        ButterKnife.bind(this);
        DaggerKalkulatorComponent.create().suntik(this);

        new Thread(() -> {
            try {
                tampilkanText("Ok, wait..." + PAGE);
                Thread.sleep(2500);
                runDependencySin();
                Thread.sleep(2500);
                runDependencyCos();
                Thread.sleep(2500);
                runDependencyTan();
                Thread.sleep(2500);
                tampilkanText("Hahay, Selesai..." + PAGE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @OnClick(R.id.textView)
    public void lanjutKeYangLain(View view) {
        startActivity(new Intent(this, ManualDiActivity.class));
    }

    private void runDependencySin() {
        double hasil = kalkulator.getSin().hitung(10);
        tampilkanText("sin 10 = " + hasil);
    }

    private void runDependencyCos() {
        double hasil = kalkulator.getCos().hitung(10);
        tampilkanText("cos 10 = " + hasil);
    }

    private void runDependencyTan() {
        double hasil = kalkulator.getTan().hitung(10);
        tampilkanText("Tan 10 = " + hasil);
    }

    private void tampilkanText(final String hasil) {
        textView.post(() -> textView.setText(hasil));
    }
}