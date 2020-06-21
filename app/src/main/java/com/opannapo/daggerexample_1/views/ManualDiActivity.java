package com.opannapo.daggerexample_1.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.opannapo.daggerexample_1.R;
import com.opannapo.daggerexample_1.dependencies.Bagi;
import com.opannapo.daggerexample_1.dependencies.Cos;
import com.opannapo.daggerexample_1.dependencies.Kali;
import com.opannapo.daggerexample_1.dependencies.Kalkulator;
import com.opannapo.daggerexample_1.dependencies.Kurang;
import com.opannapo.daggerexample_1.dependencies.Sin;
import com.opannapo.daggerexample_1.dependencies.Tambah;
import com.opannapo.daggerexample_1.dependencies.Tan;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManualDiActivity extends AppCompatActivity {
    Kalkulator kalkulator;
    Bagi bagi = new Bagi();
    Kali kali = new Kali();
    Tambah tambah = new Tambah();
    Kurang kurang = new Kurang();
    Sin sin = new Sin();
    Cos cos = new Cos();
    Tan tan = new Tan();

    @BindView(R.id.textView)
    TextView textView;

    final String PAGE = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);
        ButterKnife.bind(this);

        kalkulator = new Kalkulator(bagi, kali, kurang, tambah, sin, cos, tan);

        new Thread(() -> {
            try {
                tampilkanText("Ok, wait..." + PAGE);
                Thread.sleep(2500);
                runDependencyBagi();
                Thread.sleep(2500);
                runDependencyKali();
                Thread.sleep(2500);
                runDependencyKurang();
                Thread.sleep(2500);
                runDependencyTambah();
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
    public void balikKeYangDulu(View view) {
        finish();
    }

    private void runDependencyBagi() {
        final double hasil = kalkulator.getBagi().hitung(10, 2);
        tampilkanText("10 : 2 = " + hasil);
    }

    private void runDependencyKali() {
        final double hasil = kalkulator.getKali().hitung(10, 2);
        tampilkanText("10 x 2 = " + hasil);
    }

    private void runDependencyKurang() {
        final double hasil = kalkulator.getKurang().hitung(10, 2);
        tampilkanText("10 - 2 = " + hasil);
    }

    private void runDependencyTambah() {
        double hasil = kalkulator.getTambah().hitung(10, 2);
        tampilkanText("10 + 2 = " + hasil);

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
