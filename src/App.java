import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;
        while (isLanjutkan) {
            clearScreen();
            System.out.println("Database Bioskop\n");
            System.out.println("1. Lihat seluruh Film");
            System.out.println("2. Cari data film");
            System.out.println("3. Tambah data film");
            System.out.println("4. Ubah data film");
            System.out.println("5. Hapus data film");
            System.out.println("6. Pesan tiket");
            System.out.println("7. Menampilkan Pembelian tiket");
            System.out.println("8. Pembelian Makanan");
            System.out.println("9. Tambah Makanan");

            System.out.print("\n\nPilihan anda : ");
            pilihanUser = input.next();
            switch (pilihanUser) {
                case "1":
                    System.out.println("\n==================");
                    System.out.println("LIHAT SELURUH FILM");
                    System.out.println("==================");
                    lihatFilm();
                    break;
                case "2":
                    System.out.println("\n==============");
                    System.out.println("CARI DATA FILM");
                    System.out.println("==============");
                    cariFilm();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA FILM");
                    System.out.println("================");
                    tambahFilm();
                    lihatFilm();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA FILM");
                    System.out.println("==============");
                    updateFilm();
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA FILM");
                    System.out.println("===============");
                    deleteFilm();
                    break;
                case "6":
                    System.out.println("\n===================");
                    System.out.println("PESAN TIKET BIOSKOP");
                    System.out.println("===================");
                    pesanTiket();
                    break;
                case "7":
                    System.out.println("\n===========================");
                    System.out.println("Menampilkan Pembelian Tiket");
                    System.out.println("===========================");
                    historyPesanTiket();
                    break;
                case "8":
                    System.out.println("\n=================");
                    System.out.println("Pesan MAKANAN");
                    System.out.println("=================");
                    pesanMakanan();
                    break;
                case "9":
                    System.out.println("\n===================");
                    System.out.println("Tambah Menu Makanan");
                    System.out.println("===================");
                    tambahMenu();
                    break;
                default:
                    System.out.print("Inputan anda tidak ditemukan\nSilahkan masukkan 1-9");
            }
            isLanjutkan = getYesorNo("Apakah anda ingin melanjutkan");
        }
    }

    private static void lihatFilm() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("src/film.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            // tambahData();
            return;
        }
        System.out
                .print("---------------------------------------------------------------------------------------------");
        System.out.println(
                "\n| NO |\tJudul Film            |\tTANGGAL RILIS FILM    |\tPRODUSER              |\tSTUDIO");
        System.out
                .println(
                        "---------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while (data != null) {
            nomorData++;

            System.out.printf("| %2d ", nomorData);
            tampilanFilm(data);

            data = bufferInput.readLine();
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        fileInput.close();
        bufferInput.close();
    }

    private static void cariFilm() throws IOException {
        // Membaca file data.txt
        try {
            File file = new File("src/Data.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahFilm();
            return;
        }
        // Memasukkan keyword dari user
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan kata kunci pencarian Film: ");
        String pencarian = input.nextLine();

        String[] keywords = pencarian.split("\\s+");

        // Kita cek
        cekData(keywords, true);
    }

    private static boolean cekData(String[] keywords, boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("src/film.txt");
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        String data = bufferedInput.readLine();
        if (isDisplay) {
            System.out
                    .print("---------------------------------------------------------------------------------------------");
            System.out.println(
                    "\n| NO |\tJudul Film            |\tTANGGAL RILIS FILM    |\tPRODUSER              |\tSTUDIO");
            System.out
                    .println(
                            "---------------------------------------------------------------------------------------------");
        }
        boolean isExist = false;
        int nomorData = 0;
        while (data != null) {
            // Cek keyword per baris
            isExist = true;

            for (String keyword : keywords) {
                // menentukan apakah data mengandung keyword
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            // Jika keywordnya cocok akan ditampilkan
            if (isExist) {
                if (isDisplay) {
                    nomorData++;
                    System.out.printf("| %2d ", nomorData);
                    tampilanFilm(data);
                } else {
                    break;
                }
            }
            data = bufferedInput.readLine();
        }
        if (isDisplay) {
            System.out.println(
                    "---------------------------------------------------------------------------------------------");
        }
        return isExist;
    }

    private static void tambahFilm() throws IOException {
        FileWriter fileOutput = new FileWriter("src/film.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // mengambil input dari user
        Scanner input = new Scanner(System.in);
        String judulFilm, tanggalFilm, sutradara, studio, jmlTiket, harga;
        System.out.print("Masukkan judul film: ");
        judulFilm = input.nextLine();
        System.out.print("Masukkan tanggal tayang: ");
        tanggalFilm = input.nextLine();
        System.out.print("Masukkan nama sutradara: ");
        sutradara = input.nextLine();
        System.out.print("Masukkan nama studio: ");
        studio = input.nextLine();
        System.out.print("Masukkan jumlah tiket: ");
        jmlTiket = input.nextLine();
        System.out.print("Masukkan harga tiket: ");
        harga = input.nextLine();
        StringTokenizer st = new StringTokenizer(tanggalFilm, " ");
        st.nextToken();
        st.nextToken();
        String tahun = st.nextToken();
        String[] keywords = {
                judulFilm + "," + tanggalFilm + "," + sutradara + "," + studio + "," + jmlTiket + "," + harga };
        boolean isExist = cekData(keywords, false);

        // Menulis data di data.text
        if (!isExist) {
            String judulTanpaSpasi = judulFilm.replaceAll("\\s+", "");
            String primaryKey = judulTanpaSpasi + "_" + tahun;
            System.out.println("\nData yang akan anda masukan adalah");
            System.out.println("--------------------------------------");
            System.out.println("Primary key    : " + primaryKey);
            System.out.println("Judul Film     : " + judulFilm);
            System.out.println("Tanggal Tayang : " + tanggalFilm);
            System.out.println("Sutradara      : " + sutradara);
            System.out.println("studio         : " + studio);
            System.out.println("jmlTiket       : " + jmlTiket);
            System.out.println("harga          : " + harga);

            boolean isTambah = getYesorNo("Apakah anda ingin menambah data");

            if (isTambah) {
                bufferOutput.write(primaryKey + "," + judulFilm + "," + tanggalFilm + "," + sutradara
                        + "," + studio + "," + jmlTiket + "," + harga);
                bufferOutput.newLine();// menambahkan baris baru
                bufferOutput.flush();// digunakan untuk menulis data ke data.text
            }

        } else {
            System.out.println("Film yang anda akan masukkan sudah tersedia dengan data sebagai berikut : ");
            cekData(keywords, true);
        }
        bufferOutput.close();
    }

    private static void updateFilm() throws IOException {
        File database = new File("src/film.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // membuat database sementara
        File temp = new File("src/temp.txt");
        FileWriter fileOutput = new FileWriter(temp);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // membuat tampilan data
        System.out.println("LIst Film");
        lihatFilm();

        // ambil user input
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nomor film yang akan diupdate : ");
        int updateNum = input.nextInt();

        // tampilan data yang ingin diupdate
        String data = bufferedInput.readLine();
        int dataCount = 0;
        while (data != null) {
            dataCount++;

            StringTokenizer st = new StringTokenizer(data, ",");

            // Tampilkan datacount == updatenum
            if (updateNum == dataCount) {
                System.out.println("\nData yang ingin anda update adalah:");
                System.out.println("-----------------------------------");
                System.out.println("Primary Key        : " + st.nextToken());
                System.out.println("Judul Film         : " + st.nextToken());
                System.out.println("Tanggal Film       : " + st.nextToken());
                System.out.println("Sutradara          : " + st.nextToken());
                System.out.println("Studio             : " + st.nextToken());
                System.out.println("Stok               : " + st.nextToken());
                System.out.println("Harga              : " + st.nextToken());

                // Update data

                // Mengambil input dari user
                String[] fieldData = { "Judul Film", "Tanggal Film", "Sutradara", "Studio", "Stok", "Harga" };
                String[] tempData = new String[6];
                // refresh token
                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();
                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = getYesorNo("Apakah anda ingin merubah " + fieldData[i]);
                    originalData = st.nextToken();
                    if (isUpdate) {
                        // User input

                        input = new Scanner(System.in);
                        System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
                        tempData[i] = input.nextLine();

                    } else {
                        tempData[i] = originalData;
                    }
                }

                // Tampilkan data baru kelayar
                st = new StringTokenizer(data, ",");
                st.nextToken();
                System.out.println("\nData baru anda adalah:");
                System.out.println("-----------------------------------");
                System.out.println("Judul Film         : " + st.nextToken() + " ==> " + tempData[0]);
                System.out.println("Tanggal Film       : " + st.nextToken() + " ==> " + tempData[1]);
                System.out.println("Sutradara          : " + st.nextToken() + " ==> " + tempData[2]);
                System.out.println("Studio             : " + st.nextToken() + " ==> " + tempData[3]);
                System.out.println("Stok               : " + st.nextToken() + " ==> " + tempData[4]);
                System.out.println("Harga              : " + st.nextToken() + " ==> " + tempData[5]);

                boolean isUpdate = getYesorNo("Apakah anda yakin mengupdate data tersebut");

                if (isUpdate) {

                    // Cek data baru di database
                    boolean isExist = cekData(tempData, false);
                    if (isExist) {
                        System.err
                                .println("Data buku sudah ada, proses update batal, \nSilahkan delete data yang sama");
                        bufferedOutput.write(data);

                    } else {

                        // Format data baru ke dalam database
                        String judulFilm = tempData[0];
                        String tanggalFilm = tempData[1];
                        String sutradara = tempData[2];
                        String studio = tempData[3];
                        String stok = tempData[4];
                        String harga = tempData[5];
                        st = new StringTokenizer(tanggalFilm, " ");
                        st.nextToken();
                        st.nextToken();
                        String tahun = st.nextToken();
                        // fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
                        String judulTanpaSpasi = judulFilm.replaceAll("\\s+", "");
                        String primaryKey = judulTanpaSpasi + "_" + tahun;
                        // Menulis data ke database
                        bufferedOutput.write(primaryKey + "," + judulFilm + "," + tanggalFilm + "," + sutradara + ","
                                + studio + "," + stok + "," + harga);
                    }
                } else {
                    bufferedOutput.write(data);
                }
            } else {
                // copy data
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();
            data = bufferedInput.readLine();
        }
        // Menulis data ke file
        bufferedOutput.flush();
        bufferedInput.close();
        bufferedOutput.close();
        fileInput.close();
        fileOutput.close();
        System.gc();
        // Delete data original
        database.delete();
        // rename file temp ke data
        temp.renameTo(database);
    }

    private static void deleteFilm() throws IOException {
        // Mengambil ambil data awal
        File database = new File("src/film.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // Membuat database sementara
        File temp = new File("src/temp.txt");
        FileWriter fileOutput = new FileWriter(temp);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List Film");
        lihatFilm();
        // ambil input user
        Scanner input = new Scanner(System.in);
        System.out.print("\n\nMasukkan nomer film yang akan dihapus : ");
        int deleteNum = input.nextInt();

        // Looping untuk mmebaca tiap baris data dan skip data yang akan dihapus
        int dataCount = 0;
        String data = bufferedInput.readLine();
        boolean isFound = false;
        while (data != null) {
            dataCount++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");
            // tampilkan data yang ingin dihapus
            if (deleteNum == dataCount) {
                System.out.println("\nData yang ingin anda hapus adalah:");
                System.out.println("----------------------------------");
                System.out.println("Primary Key        : " + st.nextToken());
                System.out.println("Judul Film         : " + st.nextToken());
                System.out.println("Tanggal Film       : " + st.nextToken());
                System.out.println("Sutradara          : " + st.nextToken());
                System.out.println("Studio             : " + st.nextToken());
                System.out.println("Stok               : " + st.nextToken());
                System.out.println("Harga              : " + st.nextToken());

                isDelete = getYesorNo("Apakah anda yakin akan menghapus");
                isFound = true;
            }

            if (isDelete) {
                // Skip pindahkan data dari original ke temp
                System.out.println("Data berhasil dihapus");
            } else {
                // Pindahkan data ke temp
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }
        if (!isFound) {
            System.err.println("Film tidak ditemukan");
        }
        // Menulis data ke file
        bufferedOutput.flush();
        bufferedInput.close();
        bufferedOutput.close();
        fileInput.close();
        fileOutput.close();
        System.gc();
        // Delete data original
        database.delete();
        // rename file temp ke data
        temp.renameTo(database);
    }

    private static void pesanTiket() throws IOException {
        clearScreen();
        FileWriter fileOutput = new FileWriter("src/PembelianTiket.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        File database = new File("src/film.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);
        System.out.println("LIst Film");
        lihatFilm();
        String namaFilm = "", hargaFilm;
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nomor film yang akan ditonton : ");
        int pilihFilm = input.nextInt();
        String dataFilm = bufferedInput.readLine();
        int dataCount = 0;
        while (dataFilm != null) {
            dataCount++;

            StringTokenizer st = new StringTokenizer(dataFilm, ",");

            // Tampilkan datacount == pilihFilm
            if (pilihFilm == dataCount) {

                // Tampilkan data baru kelayar
                st = new StringTokenizer(dataFilm, ",");
                st.nextToken();
                namaFilm = st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                hargaFilm = st.nextToken();
                int totalTiket;
                System.out.print("Masukkan jumlah tiket: ");
                totalTiket = input.nextInt();
                String tanggalSekarang = getTanggal();
                int totalharga = Integer.parseInt(hargaFilm) * totalTiket;
                int diskon = tampilanDiskon();
                int totalBayar = totalharga - totalharga * diskon / 100;
                String primaryKey = tanggalSekarang + "_" + namaFilm + "_" + totalTiket;
                System.out.println("\nData yang akan anda masukan adalah");
                System.out.println("--------------------------------------");
                System.out.println("Primary key  : " + primaryKey);
                System.out.println("Nama Film    : " + namaFilm);
                System.out.println("Tanggal      : " + tanggalSekarang);
                System.out.println("Total TIket  : " + totalTiket);
                System.out.println("Total Harga  : " + totalharga);
                System.out.println("Diskon       : " + diskon + "%");
                System.out.println("Total Bayar  : " + totalBayar);
                boolean isPesan = getYesorNo("Apakah anda ingin pesan tiket");
                if (isPesan) {
                    bufferOutput.write(primaryKey + "," + namaFilm + "," + tanggalSekarang + "," + totalTiket + ","
                            + totalharga + "," + diskon + "," + totalBayar);
                    bufferOutput.newLine();
                    bufferOutput.flush();
                }
            }
            dataFilm = bufferedInput.readLine();
        }
        if (namaFilm == "") {
            System.out.println("Maaf Film tidak tersedia !");
            pesanTiket();
        }
        bufferOutput.close();
    }

    private static void historyPesanTiket() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("src/PembelianTiket.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            pesanTiket();
            return;
        }
        System.out
                .print("-----------------------------------------------------------------------------------------------------------------");
        System.out.println(
                "\n| NO |\tJudul Film            |\tTANGGAL MENONTON      |\tJUMLAH TIKET |\tTOTAL HARGA  |\tDISKON  |   TOTAL BAYAR");
        System.out
                .println(
                        "-----------------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while (data != null) {
            nomorData++;

            System.out.printf("| %2d ", nomorData);
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("|\t%-20s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s  ", stringToken.nextToken());
            System.out.printf("|\t%-11s  ", stringToken.nextToken());
            System.out.printf("|\t%-13s", stringToken.nextToken());
            System.out.printf("|\t%-8s", stringToken.nextToken());
            System.out.printf("|\t%-10s", stringToken.nextToken());
            System.out.printf("\n");

            data = bufferInput.readLine();
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        fileInput.close();
        bufferInput.close();
    }

    private static void lihatMenu() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("src/Menu.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahMenu();
            return;
        }
        System.out
                .print("-----------------------------------------");
        System.out.println(
                "\n| NO |\tNAMA MAKANAN     | HARGA      ");
        System.out
                .println(
                        "-----------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while (data != null) {
            nomorData++;

            System.out.printf("| %2d ", nomorData);
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            System.out.printf("|\t%-15s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s  ", stringToken.nextToken());
            System.out.printf("\n");

            data = bufferInput.readLine();
        }
        System.out.println(
                "-----------------------------------------");
        fileInput.close();
        bufferInput.close();
    }

    private static void pesanMakanan() throws IOException {
        clearScreen();
        FileWriter fileOutput = new FileWriter("src/PembelianMakanan.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        File database = new File("src/Menu.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);
        System.out.println("List Makanan");
        lihatMenu();
        String namaMakanan = "", hargaMakanan;
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nomor makanan yang akan dipesan : ");
        int pilihMakanan = input.nextInt();
        String dataMakanan = bufferedInput.readLine();
        int dataCount = 0;
        while (dataMakanan != null) {
            dataCount++;

            StringTokenizer st = new StringTokenizer(dataMakanan, ",");

            // Tampilkan datacount == pilihMakanan
            if (pilihMakanan == dataCount) {

                // Tampilkan data baru kelayar
                st = new StringTokenizer(dataMakanan, ",");
                namaMakanan = st.nextToken();
                hargaMakanan = st.nextToken();
                int totalPesan;
                System.out.print("Masukkan jumlah pesan : ");
                totalPesan = input.nextInt();
                String tanggalSekarang = getTanggal();
                int totalharga = Integer.parseInt(hargaMakanan) * totalPesan;
                String primaryKey = tanggalSekarang + "_" + namaMakanan + "_" + totalPesan;
                System.out.println("\nData yang akan anda masukan adalah");
                System.out.println("--------------------------------------");
                System.out.println("Primary key  : " + primaryKey);
                System.out.println("Nama Makanan : " + namaMakanan);
                System.out.println("Tanggal      : " + tanggalSekarang);
                System.out.println("Total Pesan  : " + totalPesan);
                System.out.println("Total Harga  : " + totalharga);
                boolean isPesan = getYesorNo("Apakah anda ingin pesan ini");
                if (isPesan) {
                    bufferOutput.write(primaryKey + "," + namaMakanan + "," + tanggalSekarang + "," + totalPesan + ","
                            + totalharga);
                    bufferOutput.newLine();
                    bufferOutput.flush();
                }
            }
            dataMakanan = bufferedInput.readLine();
        }
        if (namaMakanan == "") {
            System.out.println("Maaf Film tidak tersedia !");
            pesanTiket();
        }
        bufferOutput.close();
    }

    private static void tambahMenu() throws IOException {
        FileWriter fileOutput = new FileWriter("src/Menu.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // mengambil input dari user
        Scanner input = new Scanner(System.in);
        String namaMakanan, hargaMakanan;
        System.out.print("Masukkan nama makanan : ");
        namaMakanan = input.nextLine();
        System.out.print("Masukkan Harga makanan: ");
        hargaMakanan = input.nextLine();

        String[] keywords = { namaMakanan + "," + hargaMakanan };

        boolean isExist = cekDataMakanan(keywords, false);

        // Menulis data di data.text
        if (!isExist) {
            System.out.println("\nData yang akan anda masukan adalah");
            System.out.println("--------------------------------------");
            System.out.println("Nama Makanan        : " + namaMakanan);
            System.out.println("Harga Makanan       : " + hargaMakanan);

            boolean isTambah = getYesorNo("Apakah anda ingin menambah menu");

            if (isTambah) {
                bufferOutput.write(namaMakanan + "," + hargaMakanan);
                bufferOutput.newLine();// menambahkan baris baru
                bufferOutput.flush();// digunakan untuk menulis data ke data.text
            }

        } else {
            System.out.println("Menu yang anda akan masukkan sudah tersedia dengan data sebagai berikut : ");
            cekDataMakanan(keywords, true);
        }
        bufferOutput.close();
    }

    private static boolean cekDataMakanan(String[] keywords, boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("src/Menu.txt");
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        String data = bufferedInput.readLine();
        if (isDisplay) {
            System.out
                    .print("-----------------------------------------");
            System.out.println(
                    "\n| NO |\tNAMA MAKANAN     | HARGA      ");
            System.out
                    .println(
                            "-----------------------------------------");
        }
        boolean isExist = false;
        int nomorData = 0;
        while (data != null) {
            // Cek keyword per baris
            isExist = true;

            for (String keyword : keywords) {
                // menentukan apakah data mengandung keyword
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            // Jika keywordnya cocok akan ditampilkan
            if (isExist) {
                if (isDisplay) {
                    nomorData++;
                    System.out.printf("| %2d ", nomorData);
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    System.out.printf("|\t%-15s  ", stringToken.nextToken());
                    System.out.printf("|\t%-20s  ", stringToken.nextToken());
                    System.out.printf("\n");
                } else {
                    break;
                }
            }
            data = bufferedInput.readLine();
        }
        if (isDisplay) {
            System.out.println(
                    "-----------------------------------------");
        }
        return isExist;
    }

    private static String getTanggal() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

    private static int tampilanDiskon() {
        Date hariIni = new Date();
        int diskon = 0;
        if (hariIni.getDay() == 0 || hariIni.getDay() == 6) {
            return diskon = 0;
        } else {
            return diskon = 20;
        }
    }

    private static void tampilanFilm(String data) {

        StringTokenizer stringToken = new StringTokenizer(data, ",");

        stringToken.nextToken();
        System.out.printf("|\t%-20s  ", stringToken.nextToken());
        System.out.printf("|\t%-20s  ", stringToken.nextToken());
        System.out.printf("|\t%-20s  ", stringToken.nextToken());
        System.out.printf("|\t%s", stringToken.nextToken());
        System.out.print("\n");
    }

    private static boolean getYesorNo(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print("\n" + message + " (y/n) : ");
        String pilihanUser = input.next();
        if (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Pilihan anda bukan y atau n");
            // System.out.print("\n" + message + " (y/n) : ");
            // pilihanUser = input.next();
            getYesorNo(message);
        }
        return pilihanUser.equalsIgnoreCase("y");
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception e) {
            System.err.println("Tidak bisa clear screen");
        }
    }
}