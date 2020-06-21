package com.opannapo.daggerexample_1.dependencies;

import javax.inject.Inject;

/**
 * Created by napouser on 21,June,2020
 */
public class Bagi {

    @Inject
    public Bagi() {
    }

    public double hitung(double a, double b) {
        return (a / b);
    }
}
