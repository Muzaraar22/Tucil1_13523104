# IF2211_Tucil1_13523104
A brute-force solver for the IQ Puzzler Pro game, implemented in Java.

# Puzzle Solver

## a. Deskripsi Program
**Puzzle Solver** adalah program untuk menyelesaikan puzzle penyusunan blok berbasis teks. Program membaca input dari file yang berisi:
- Ukuran papan (board).
- Mode (DEFAULT atau CUSTOM).
- Definisi blok-blok yang akan ditempatkan pada papan.

Menggunakan algoritma backtracking, program akan mencari solusi penempatan blok sehingga seluruh papan terisi tanpa tumpang tindih.

## b. Requirement Program dan Instalasi
- **Java Development Kit (JDK)**: Pastikan JDK versi 8 atau lebih baru telah terinstal.
- **Sistem Operasi**: Dapat dijalankan pada Windows, Linux, atau macOS yang mendukung Java.
- **Library Eksternal**: Tidak diperlukan (semuanya menggunakan API bawaan Java).

## c. Cara Mengkompilasi Program
Pastikan struktur direktori proyek Anda seperti berikut:

bash
Copy
project/
├── src/         (kode sumber Java)
├── bin/         (folder output file .class)
├── test/        (file test, misalnya testcast.txt, serta folder solution)
└── doc/         (dokumentasi)
Untuk mengkompilasi program, buka terminal/command prompt di direktori root proyek, kemudian jalankan:

Untuk mengkompilasi program, buka terminal atau command prompt di direktori root proyek, lalu jalankan:
```bash
javac -d bin src/*.java

nginx
Copy
javac -d bin src/*.java
Perintah di atas akan mengkompilasi semua file Java di dalam folder src dan menghasilkan file .class di dalam folder bin.

d. Cara Menjalankan dan Menggunakan Program
Menjalankan melalui Command Line
Setelah kompilasi berhasil, jalankan program dengan perintah:
bash
Copy
java -cp bin src.Main
Program akan meminta Anda memasukkan path file input (misalnya test/testcast.txt atau file lain sesuai dengan format yang telah ditentukan).
Membuat dan Menjalankan File JAR Executable
Jika Anda ingin membuat file JAR agar dapat dijalankan secara lebih mudah:

Buat file manifest (misalnya manifest.txt) dengan isi berikut:
css
Copy
Main-Class: src.Main
(Pastikan ada newline di akhir file.)
Buat file JAR dengan perintah:
python
Copy
jar cfm app.jar manifest.txt -C bin .
Jalankan file JAR dengan perintah:
nginx
Copy
java -jar app.jar
