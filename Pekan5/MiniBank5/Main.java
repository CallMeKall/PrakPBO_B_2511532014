package MiniBank5;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null;
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Rekening");
			System.out.println("6. Cetak Mutasi");
			System.out.println("7. 3 Riwayat Terakhir");
			System.out.println("8. Ganti PIN");
			System.out.println("9. Simulasi Akhir Bulan");
			System.out.println("10. Transfer ke Rekening Lain");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch(pilihan) {
				case 1:
					System.out.print("Masukan No Rekening: ");
					String no = input.nextLine();
					System.out.print("Masukan Nama Pemilik: ");
					String nama = input.nextLine();
					System.out.print("Masukan Saldo Awal: ");
					double saldo = input.nextDouble();
					input.nextLine();
					System.out.print("Masukan PIN awal: ");
					String pinAwal = input.nextLine();
					
					System.out.print("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis | 3. Tabungan VIP:  ");
					String produk = input.nextLine();
					
					Rekening rekeningBaru = null;
					
					if (produk.equals("1")){
						System.out.print("Suku Bunga (dalam Persen): ");
						double sukuBunga = input.nextDouble();
						rekeningBaru = new RekeningTabungan(no, nama, saldo, pinAwal, sukuBunga);
					} else if (produk.equals("2")) {
						System.out.print("Limit Pinjaman: ");
						double limit  = input.nextDouble();
						rekeningBaru = new RekeningGiro(no, nama, saldo, pinAwal, limit);
					} else if (produk.equals("3")) {
						System.out.println("Rekening anda adalah VIP, langsung mendapatkan bonus Rp100,000.00!");
						rekeningBaru = new RekeningVIP(no, nama, saldo, pinAwal);
					} else {
						System.out.println("Pilihan tidak valid!");
						break;
					}
					
					daftarRekening.add(rekeningBaru);
					akunAktif = rekeningBaru;
					
					break;
					
				case 2:
					if (akunAktif == null) {
						System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
					} else {
						System.out.print("Masukan nominal setor: ");
						double setor = input.nextDouble();
						akunAktif.setorTunai(setor);
					}
					break;
					
				case 3:

					if (akunAktif == null) {
						System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
					} else {
						System.out.print("Masukan PIN: ");
						String pin = input.nextLine();
						if (akunAktif.otentikasi(pin)) {
							System.out.print("Masukan nominal penarikan: ");
							double tarik = input.nextDouble();
							akunAktif.tarikTunai(tarik);
						} else {
							System.out.println("Akses Ditolak: PIN yang anda masukkan salah!");
						}
						
					}
					break;
					
				case 4:
					if (akunAktif == null) {
						System.out.println("Error: Anda belum membuka rekening!");
					} else {
						akunAktif.cekInformasi();
					}
					break;
				
				case 5:
					if (daftarRekening.isEmpty()) {
						System.out.println("Belum ada rekening yang terdaftar di sistem");
						break;
					}
					
					System.out.print("Masukan nomor rekening yang ingin diaktifkan: ");
					String cari = input.nextLine();
					boolean ditemukan = false;
					
					for (Rekening rekening : daftarRekening) {
						if (rekening.getNomorRekening().equals(cari)) {
							akunAktif = rekening;
							System.out.println("Rekening berhasil diubah ke pemilik: " + rekening.getNamaPemilik());
							ditemukan = true;
							break;
						}
					}
					if (!ditemukan) {
						System.out.println("Nomor rekening tidak ditemukan");
					}
					break;
					
				case 6:
					if (akunAktif.riwayatTransaksi.isEmpty()) {
						System.out.println("Belum ada transaksi pada akun ini");
						break;
					}
					System.out.print("Masukan PIN: ");
					String pin = input.nextLine();
					if (akunAktif.otentikasi(pin)) {
						System.out.println("Riwayat Transaksi");
						akunAktif.cetakMutasi();
						
					}else {
						System.out.println("Akses Ditolak: PIN yang anda masukkan salah!");
					}
					break;
					
				case 7:
					if (daftarRekening.isEmpty()) {
						System.out.println("Belum ada rekening yang terdaftar di sistem");
						break;
					}
					for (int i = Math.max(0, akunAktif.riwayatTransaksi.size() - 3); i < akunAktif.riwayatTransaksi.size(); i++) {
						akunAktif.riwayatTransaksi.get(i).cetakDetail();
					}
					break;
				
				case 8:
					if (daftarRekening.isEmpty()) {
						System.out.println("Belum ada rekening yang terdaftar di sistem");
						break;
					}
					System.out.print("Masukan PIN: ");
					String pinLama = input.nextLine();
					if (akunAktif.otentikasi(pinLama)) {
						System.out.print("Masukan PIN Baru: ");
						String pinBaru = input.nextLine();
						if (!pinLama.equals(pinBaru)) {
							akunAktif.gantiPin(pinBaru);
						} else {
							System.out.println("PIN baru harus berbeda dengan PIN lama");
						}
					}
					break;
				
				case 9:
					if (akunAktif instanceof RekeningTabungan) {
						((RekeningTabungan) akunAktif).tambahBungaAkhirBulan();
					} else {
						System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan");
					}
					break;
					
					case 10:
					    if (akunAktif == null) {
					        System.out.println("Error: Anda belum memiliki rekening aktif!");
					        break;
					    }
					
					    System.out.print("Masukkan nomor rekening tujuan: ");
					    String nomorTujuan = input.nextLine();
					
					    Rekening akunTujuan = null;
					
					    // Cari rekening tujuan
					    for (Rekening rekening : daftarRekening) {
					        if (rekening.getNomorRekening().equals(nomorTujuan)) {
					            akunTujuan = rekening;
					            break;
					        }
					    }
					
					    if (akunTujuan == null) {
					        System.out.println("Rekening tujuan tidak ditemukan!");
					        break;
					    }
					
					    if (akunTujuan == akunAktif) {
					        System.out.println("Gagal: Tidak dapat transfer ke rekening sendiri!");
					        break;
					    }
					
					    System.out.print("Masukkan nominal transfer: Rp");
					    double nominal = input.nextDouble();
					    input.nextLine();
					
					    System.out.print("Masukkan PIN: ");
					    String pinTransfer = input.nextLine();
					
					    akunAktif.transfer(akunTujuan, nominal, pinTransfer);
					    break;

				case 0:
					isRunning = false;
					System.out.println("Sistem ditutup. Terima Kasih!");
					break;
				
				default:
					System.out.println("Pilihan tidak valid");
			}
		}
		input.close();
	}
}
