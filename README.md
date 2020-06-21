# dagger_example_1
Android (Java) Dagger2 example 1. Basic : @Component, @Inject



# Bukabukaan
Kalau pada umumnya orang memulai dengan Pembukaan, disini gue sebut dengan bukabukaan.<br>
Yang pasti artikel yang gue tulis di README.MD ini cuma layak dibaca sama orang-orang (nubi) seperti gue yang bernasib sama, yaitu malas haha.<br>
Selama ini gue menutup diri dari apa manfaat Dagger. Lebay, ikut-ikutan, sok kekinian, dan lain-lain (Pendapat Gue Dulu)...<br>
Sebenernya bukan itu sih masalah utamanya, wkwkww....<br>
Yang jelas gue gagal memahami bagaimana cangkul yg satu ini bisa sangat bermanfaat. <br>
Sampai pada akhirnya saking penasaran dengan barang yang sudah cukup lama hadir dipasar malam ini, gue mencoba dengan simulasi project simple. <br>
Dan ternyata, tereng-tereng-tereng gue berhasil membuktikan (untuk diri-sendiri) bahwa dagger bisa mencegah kekacauan besar diakhir jaman disaat aplikasi sudah semakin gendut dan butuh perubahan-perubahan konsumsi makanan. Sourcecode Project ini cuma berisikan contoh simple yang hanya mengimplementasikan Componen & Inject.<br>
Setelah memahami ini Dagger akan menjadi sangat menarik dan mudah dikulik (Buat yg suka ngulik).


# Tak kenal maka tak sayang.
Itu tepat, tapi untuk koding kayaknya belum bisa dibilang kenal kalau belum ngerti cara menggunakanya dan apa maksud & tujuan sebuah framework diciptakan.<br>
Ujung-ujungnya cuma bergelut dengan copy-paste & error message yang seadanya. <br>
Ujung-ujungnya juga gak tau harus searching dengan keyword apa buat cari solusinya.<br>
'Buah mengkudu pahit rasanya, air disungai mengalir tanang. <br>
Mari lanjut, kita bahas apa gunanya'<br>


# Buat apa, apa gunanya ?
Ada banyak manfaat Dagger, silahkan dicari tau dibanyak forum sudah dijelaskan. <br>
Tapi yang pasti Dagger adalah Depencies Injection Framework yang bekerja bukan dilevel runtime.<br>
Annotation yang dipake memang retentionnya RUNTIME, tapi dia punya annotation processor sendiri yang bekerja pada saat applikasi dibuild.<br>
Intinya barang ini bekerja dengan cara men-generate class-class static yang membuat applikasi kita tidak membutuhkan operasi manual untuk implementasi depencies injection, sama halnya seperti Pisau Mentega (ButterKnife). Jadi tidak perlu khawatir takut applikasi kita ketambahan proses yang bukan-bukan, dia cuma bantu generatin supaya bisa kita pake, itu tok.<br>
Penjelasan mengenai apa yang membuat mata gue terbuka lebar-lebar akan dijelaskan dibawah.<br>

## Contoh Kasus
Sama seperti nubi lainya, waktu itu gue mulai dengan aplikasi kalkulator, aplikasi sederhana yang butuh module Kalkulator. Class Kalkulator ini memiliki ketergantungan pada beberapa class lainya yang memiliki proses berbeda-beda, misalnya : Kali,Bagi,Tambah,Kurang.

```
public class Kalkulator {
    private final Bagi bagi;
    private final Kali kali;
    private final Kurang kurang;
    private final Tambah tambah;

 
    public Kalkulator(Bagi bagi, Kali kali, Kurang kurang, Tambah tambah) {
        this.bagi = bagi;
        this.kali = kali;
        this.kurang = kurang;
        this.tambah = tambah; 
    }

    public Bagi getBagi() {return bagi;}

    public Kali getKali() {return kali;}

    public Kurang getKurang() {return kurang;}

    public Tambah getTambah() {return tambah;} 
}  

public class Kali { 
    public Kali() {} 
    public double hitung(double a, double b) {return (a * b);}
} 

public class Bagi { 
    public Bagi() {} 
    public double hitung(double a, double b) {return (a / b);}
}

public class Bagi { 
    public Bagi() {} 
    public double hitung(double a, double b) {return (a / b);}
}

public class Tambah { 
    public Tambah() {} 
    public double hitung(int a, int b) {return a + b;}
}

public class Kurang {
    @Inject
    public Kurang() {} 
    public double hitung(int a, int b) {return a - b;}
}

```

Nah kemudian Modul ini gue pake di ManualDiActivity, dengan implementasi manual DI sederhana seperti pada umumnya.

```
public class ManualDiActivity extends AppCompatActivity {
    Kalkulator kalkulator;
    Bagi bagi = new Bagi();
    Kali kali = new Kali();
    Tambah tambah = new Tambah();
    Kurang kurang = new Kurang(); 

    @BindView(R.id.textView)
    TextView textView;

    final String PAGE = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);
        ButterKnife.bind(this);

        kalkulator = new Kalkulator(bagi, kali, kurang, tambah);

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
                tampilkanText("Hahay, Selesai..." + PAGE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    ...
    ..
    . 

}

```

Tidak ada yang salah dengan implementasi seperti itu, tapi gue ngotot pokoknya implementasi ini harus bermasalah diakhir jaman setelah project mulai gendut dan mengalami perubahan kebutuhan.<br>

## Simulasi Menciptakan Ketergantungan Pada Satu Object Di Banyak Tempat
Oke, akhirnya gue kepikiran bagaimana caranya agar aplikasi ini menjadi gendut dan butuh Modul Kalkulator dibanyak tempat.<br>
Akhirnya gue kepikiran buat duplicate ManualDiActivity sebanyak 100 class (Gue generate sendiri pake script. Bikin aja gak susah, tinggal write & replace, trus modify juga Manifestnya).<br>
Dan setelah menunggu Activity tadi diduplicate selama 0.0001 ms ... Jreng-jreng-jreng akhirnya sekarang gue punya 101 Acrivity, dimulai dari ManualDiActivity,ManualDiActivity1,ManualDiActivity2 sampai ManualDiActivity100.<br>
Mantap, sekarang aplikasi gue sudah gendut dan semuanya menggunakan modul Kalkulator yang objectnya diinitial dengan cara<br>
```
	...
	kalkulator = new Kalkulator(bagi, kali, kurang, tambah);
	...
```

## Menciptakan Masalah
Nah sekarang kita harus berfikir bagaimana caranya menciptakan masalah, dan masalah itu membuat orang sakit hati tapi tidak sampai bunuh diri.<br>
Awalnya tidak sengaja, tapi akhirnya gue kepikiran bakal lebih keren kalau Modul Kalkulator ini ditambahkan juga kalkulasi matematika untuk operasi sin.<br>
Okeh, akhirnya gue buat class baru untuk operasi sin, dan gue include ke module Kalkulator.<br>

```
public class Sin { 
    public Sin() {}

    public double hitung(double a) {return Math.sin(a);}
}

```

Dan Modul Kalulator sekarang berubah menjadi seperti ini:<br>

```
public class Kalkulator {
    private final Bagi bagi;
    private final Kali kali;
    private final Kurang kurang;
    private final Tambah tambah;

    private final Sin sin; 


    @Inject
    public Kalkulator(Bagi bagi, Kali kali, Kurang kurang, Tambah tambah, Sin sin) {
        this.bagi = bagi;
        this.kali = kali;
        this.kurang = kurang;
        this.tambah = tambah;
        this.sin = sin; 
    }

    ...
    ..
    .
```

Jreng-Jreng-Jreng pas dibuild, ternyata error. <br>
Gak tanggung-tanggung... 101 Activity yang gue punya tadi harus diubah implementasinya menjadi :<br>
```
public class ManualDiActivity extends AppCompatActivity {
    Kalkulator kalkulator;
    Bagi bagi = new Bagi();
    Kali kali = new Kali();
    Tambah tambah = new Tambah();
    Kurang kurang = new Kurang(); 
    Sin sin = new Sin();// --->>>> Cuma nambahin ini doang !!!! Di 101 Tempat

    @BindView(R.id.textView)
    TextView textView;

    final String PAGE = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);
        ButterKnife.bind(this);

        kalkulator = new Kalkulator(bagi, kali, kurang, tambah, sin);// --->>>> Cuma nambahin ini doang !!!! Di 101 Tempat
	...
	..
	.
    }

    ...
    ..
    . 

}

```

Tuan takur saja yang hobi menari pasti tidak betah ngerjain beginian.<br>
Untung ada tools refactor, walaupun tidak selamanya bermanfaat dan cenderung berbahaya dikasus-kasus lain yang lebih kompleks.<br>
Mau tidak mau dan penuh rasa penyesalan sudah membuat duplicate class sebanyak itu, gue tetep kerjain. Gue Refactor semuanya...<br>
Tapi lagi-lagi hati terganjal batu-batu ketidak terimaan, karna terlalu banyak duplicate code.. (wkwkwwk ada di 101 class)<br>
Akhirnya bagian Menciptakan Masalah ini sudah berhasil dilaksanakan dengan baik dan benar. Dan gue gak mau bikin masalah lagi.<br>


## Pembuktian 
Okeh, sekarang masuk ke pembuktian bagaimana Dagger bisa sangat bermanfaat untuk masalah diatas. Kita coba menggunakan implementasi dasar dari Dagger Component & Javax Inject.<br>
Seperti yang sudah diterangkan diatas, Modul Kalkulator ini mendukung operasi Kali,Bagi,Tambah,Kurang dan Sin. Kita coba tambahkan juga untuk Cos dan Tan karna masih kerabat dekat dengan Sin.<br>
Seharusnya dengan menambahkan operasi Cos & Tan pada modul Kalkulator ini tidak akan mengulangi point "Menciptakan Masalah" diatas kalau menggunakan Dagger.<br><br>

Disini biar clear, semua activity yg lama gue delete. Karna kita cuma mau buktikan apakah dagger berhasil membantu masalah dependency injection ini & membuat project terhindar dari banyak banget duplicate code.<br>

## Skenario
Skenario pembuktian : <br>
#### Tahap 1 : Sementara Modul Kalkulator hanya berisi kalkulasi Tambah,Kali,Kurang,Bagi, dan hanya digunakan oleh 3 activity.<br>
#### Tahap 2 : Lalu ternyata setelah berjalan waktu kita butuh satu activity lagi untuk proses kalkulasi sin,cos & tan dengan menggunakan modul Kalkulator yang sudah ada, oleh karena itu kita perlu mengubah modul Kalkulator serta menbambah dependency untuk processing/kalkulasi Sin,Cos dan Tan. Seharusnya setelah terjadi perubahan pada modul Kalkulator, 3 activity sebelumnya yg sudah dibuat ditahap 1 tidak perlu mengalami perubahan apa-apa.<br>


## Implementasi Skenario 1<br>
Gampangnya disini gue bikin satu Dagger Componen untuk menampung Activity-Activity yang nanti bakal di Inject. Namanya KalkulatorComponent dengan menambahkan annotation @Component.<br>
Disini gue pake method dengan nama suntik biar lebih mudah dimengerti, tapi pada umumnya orang-orang menggunakan nama inject. Tapi terserah, bisa saja dinamai dengan 'masukin' atau apapun itu.<br>

```
@Component
public interface KalkulatorComponent {
    void suntik(DaggerActivity daggerActivity); 
    void suntik(DaggerKaliBagiActivity daggerActivity); 
    void suntik(DaggerTambahKurangActivity daggerActivity);  
} 

```

Lalu Kita ubah implementasi pada Modul Kalkulator dengan penambahan annotation @Inject di constuctor.<br>
Pada akhirnya Dagger akan membuatkan kita Object-Object (Bagi,Kali,Kurang,Tambah) secara otomatis dan disuntikan kedalam Kalkulator sehingga tidak akan terjadi NPE, dan kita tidak perlu membuat initial untuk masing masing object, misalnya : Bagi bagi=new Bagi().... hahay<br>
```
public class Kalkulator {
    private final Bagi bagi;
    private final Kali kali;
    private final Kurang kurang;
    private final Tambah tambah; 


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

    ...
    ..
    .
}

```

Tapi agar masing-masing class yang menjadi dependency si Kalkulator ini bisa dicreate otomatis oleh Dagger, kita harus kasi tau Dagger dengan annotation @Inject supaya dia bisa bikinin instance untuk class tersebut. Dimana @Inject ini kita tambahkan disetiap Constuctor class-class yang dibutuhkan oleh Kalkulator. Misalnya :<br>

```
public class Bagi { 
    @Inject
    public Bagi() {} 
    public double hitung(double a, double b) {return (a / b);}
}

public class Kali {
    @Inject
    public Kali() {} 
    public double hitung(double a, double b) {return (a * b);}
}

```
<br>

Sekarang kita implementasikan pada Activity dengan menambahkan @Inject pada object Kalkulator, dan pada bagian onCreate kita kasi tau si Dagger kalau dia perlu suntik ini class.
Kurang lebih jika dibahasakan begini percakapan antara Activity dengan Dagger:<br>
Activity : Eh Dagger, tolong lu suntikin dependency buat gue ya.<br>
Dagger   : Apa yang mau di suntikin ?<br>
Activity : Lu liat lah field-field class gue mana yang ada @Injectnya.<br>
Dagger   : Oh lu butuh Kalkulator ya, ok siap laksanakan.<br>

```
public class DaggerActivity extends AppCompatActivity {
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

    ...
    ..
    .
}
```
<br>
<br>
Disini kita tidak ketergantungan untuk menciptakan Object Kalkulator dengan memenuhi semua kebutuhan parameter di constructor Kalkulator.<br>
Kita cukup menggunakan annotation @Inject agar Dagger mengenali attribut apa yang perlu dia ciptakan.<br>
<br>
<br>

## Implementasi Skenario 2<br>
Okeh, sekaranga implementasi skenario 2. Ternyata kita membutuhkan halaman / activity baru untuk proese kalkulasi sin,cos dan tan, tapi tetap menggunakan modul yang sama yaitu Kalkulator.<br>
Selanjutnya yang perlu dikalukan adalah mengubah class Kalkulator dan membuat class tambahan untuk sin,cos,tan dengan fungsi dan cara kerjanya masing-masing.<br>

```
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

    ...
    ..
    .


public class Sin {
    @Inject
    public Sin() {} 
    public double hitung(double a) {return Math.sin(a);}
}

public class Cos {
    @Inject
    public Cos() {} 
    public double hitung(double a) {return Math.cos(a);}
}
public class Tan {
    @Inject
    public Tan() {} 
    public double hitung(double a) {return Math.tan(a);}
}

```
<br>
Okeh, telah terjadi perubahan di Constuctor Kalkulator. Ada penambahan Sin, Cos dan Tan di parameter.<br>
Jika menggunakan manual DI seperti sebelumnya<br>
```
  {
  	kalkulator = new Kalkulator(bagi, kali, kurang, tambah);
  }
```
Tentu kita perlu mengubah seluruh class yang menggunakan kalkulator menjadi
```
  {
  	kalkulator = new Kalkulator(bagi, kali, kurang, tambah,sin,cos,tan);
  }
```

Ini asli ngrepotin banget, bayangkan seperti kasus sebelunmnya gue harus refactor 101 Activity, ampun !!!<br>
Supaya activity baru yang gue buat ini (untuk kalkulasi sin,cos & tan) bisa dikenali oleh Dagger dan perlu dibuatkan objectnya, maka activitynya harus ditambahkan diinterface di KalkulatorKomponen.<br>
Kalau gak, nanti gak bisa disuntik. Ini interface soalnya...<br>

```
@Component
public interface KalkulatorComponent {
    void suntik(DaggerActivity daggerActivity); 
    void suntik(DaggerKaliBagiActivity daggerActivity); 
    void suntik(DaggerTambahKurangActivity daggerActivity); 
    void suntik(DaggerSinCosTanActivity daggerActivity);
}
```
<br>
<br>
Dengan menggunakan Dagger, makan activity baru yang gue buat untuk kalkulasi sin,cos & tan cuma butuh code seperti ini.<br>
Sama seperti activty lainya, cukup inject Kalkulator dan Perintahkan didalam OnCreate supaya dagger mau suntikin dependency yang kita butuhkan.<br>

```
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


    ...
    ..
    .
```


## Kesimpulan<br>
Terbukti, dengan menggunakan Dagger, pekerjaan lebih efisien dan kopi yang sudah dibuat tidak keburu dingin, dan rokok ditangan tidak terbuang menjadi abu dengan sia-sia.<br>
Untuk example diatas, tentu pada activity tetap terjadi duplicate code yang banyak karna memang secara umum kerjanya sama.<br>
Tapi untuk real project pasti masing-masing activity punya sekup pekerjaan yang berbeda-beda.<br>
Itu saja, gue berusaha membuat penjelasan ini dengan bahasa dan skenario yang ringan dengan harapan mudah dimengerti.<br>
Kalau ada penjelasan yang salah, berarti gue sudah salah paham dan kesalah pahaman diantara kita ini mohon dimaklumi, nubi sedang berusaha.<br>
Ada banyak sekali komponen menarik yang bisa dipraktekan direal project dengan dagger, mudah-mudahan lain waktu ada ide & contoh kasus sederhana yang bisa dipakai untuk menjelaskan secara ringan bagaimana contoh project bisa saya buat. Kalau tidak ada contoh yang sederhana nanti dilain waktu kita bahas yang lebih tidak ringan (kalau saya bisa wkwkwkw)<br>
<br>
Tapi paling tidak, setelah memahami bagaimana dagger bekerja, pertolongan apa yang bisa dia berikan, tentu kita jadi tau harus explore sejauh apa, tentang apa, dan bisa tinggal disesuaikan dengan kebutuhan.
Inti dari artikel pendek banget ini adalah, "Cara lain agar jatuh cinta pada Dagger"<br><br>
Terimakasih.










