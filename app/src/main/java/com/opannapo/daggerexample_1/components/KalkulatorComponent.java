package com.opannapo.daggerexample_1.components;

import com.opannapo.daggerexample_1.views.DaggerActivity;
import com.opannapo.daggerexample_1.views.DaggerKaliBagiActivity;
import com.opannapo.daggerexample_1.views.DaggerSinCosTanActivity;
import com.opannapo.daggerexample_1.views.DaggerTambahKurangActivity;

import dagger.Component;

/**
 * Created by napouser on 21,June,2020
 */
@Component
public interface KalkulatorComponent {
    void suntik(DaggerActivity daggerActivity);

    void suntik(DaggerKaliBagiActivity daggerActivity);

    void suntik(DaggerTambahKurangActivity daggerActivity);

    void suntik(DaggerSinCosTanActivity daggerActivity);
}
