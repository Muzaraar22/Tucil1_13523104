# IF2211_Tucil1_13523104
A brute-force solver for the IQ Puzzler Pro game, implemented in Java.

# Puzzle Solver

## a. Deskripsi Program
**Puzzle Solver** - program untuk menyelesaikan puzzle dari penyusunan blok. Program membaca input dari file yang berisi:
- Ukuran papan (board) dan jumlah blok.
- Mode (DEFAULT atau CUSTOM).
- Definisi atau bentuk blok-blok yang akan ditempatkan pada papan.

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
```
Perintah tersebut akan mengompilasi semua file Java di dalam folder src dan menghasilkan file .class di dalam folder bin.

## d. Cara Menjalankan dan Menggunakan Program
Pastikan struktur direktori proyek Anda seperti berikut:

javac -d bin src/*.java
Perintah di atas akan mengkompilasi semua file Java di dalam folder src dan menghasilkan file .class di dalam folder bin.

d. Cara Menjalankan dan Menggunakan Program
- Pastikan semua file .class sudah berada di dalam folder bin.
- Buka terminal/command prompt di direktori root proyek.
- Jalankan perintah berikut:
```bash
java -cp bin src.PuzzleSolverGUI
```
- Program akan meminta path file input (misalnya test/testcast.txt).
Masukkan path sesuai file yang disediakan di folder test (bisa melalui drag and drop).
