package com.opannapo.daggerexample_1.dependencies;

import javax.inject.Inject;

/**
 * Created by napouser on 21,June,2020
 */
public class Kalkulator {
    private final Bagi bagi;
    private final Kali kali;
    private final Kurang kurang;
    private final Tambah tambah;

    private final Sin sin;
    private final Cos cos;
    private final Tan tan;


    @Inject
    public Kalkulator(Bagi bagi, Kali kali, Kurang kurang, Tambah tambah, Sin sin, Cos cos, Tan tan) {
        this.bagi = bagi;
        this.kali = kali;
        this.kurang = kurang;
        this.tambah = tambah;
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
    }

    public Bagi getBagi() {
        return bagi;
    }

    public Kali getKali() {
        return kali;
    }

    public Kurang getKurang() {
        return kurang;
    }

    public Tambah getTambah() {
        return tambah;
    }

    public Sin getSin() {
        return sin;
    }

    public Cos getCos() {
        return cos;
    }

    public Tan getTan() {
        return tan;
    }
}
