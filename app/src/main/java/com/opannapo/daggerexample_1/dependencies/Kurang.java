package com.opannapo.daggerexample_1.dependencies;

import javax.inject.Inject;

/**
 * Created by napouser on 21,June,2020
 */
public class Kurang {
    @Inject
    public Kurang() {
    }

    public double hitung(int a, int b) {
        return a - b;
    }
}
