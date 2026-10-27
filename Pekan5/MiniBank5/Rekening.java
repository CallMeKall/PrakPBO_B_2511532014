package MiniBank5;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Rekening {
	
	DecimalFormat df = new DecimalFormat("###,###.00");

	// private
	private String nomorRekening;
	private String namaPemilik;
	private String pin;
	
	protected double saldo;
	protected ArrayList<Transaksi> riwayatTransaksi;
	
	private boolean terblokir;
	private int counter;


	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		
		this.terblokir = false;
		this.counter = 0;
		
		//validasi PIN
		if (validatorPin(pinAwal)) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: Menggunakan PIN default 123456 tolong ubah secepatnya");
			this.pin = "123456";
		}
		
		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + df.format(saldo));
	}
	
	//getter
	public String getNomorRekening() { return nomorRekening;}
	public String getNamaPemilik() { return namaPemilik;}
	
	//otentikasi 
	public boolean otentikasi(String inputPin) {
	    if (terblokir) {
	        System.out.println("Akses Ditolak: Rekening Anda telah terblokir!");
	        return false;
	    }

	    if (this.pin.equals(inputPin)) {
	        counter = 0;
	        return true;
	    } else {
	        counter++;

	        System.out.println("PIN salah! Percobaan ke-" + counter);

	        if (counter >= 3) {
	            terblokir = true;
	            System.out.println("Rekening terblokir karena PIN salah 3 kali!");
	        }

	        return false;
	    }
	}
	
	public void gantiPin(String pinBaru) {
		if (validatorPin(pinBaru) == true) {
			this.pin = pinBaru;

			System.out.println("PIN berhasil diganti");
		} else {
			System.out.println("PIN gagal diganti");
		}
	}
	
	public boolean validatorPin(String that) {
		if (
		that.length() == 6 &&
		allNumber(that) ==true &&
		dup(that) == true &&
		berurutan(that) == false) {
			return true;
			
		} else {
			if (that.length() != 6) { System.out.println("PIN harus terdiri dari 6 angka"); }
			if (allNumber(that) == false) {System.out.println("PIN harus terdiri dari angka saja");}
			if (dup(that) == false) {System.out.println("PIN tidak boleh berulang");}
			if (berurutan(that) == true) {System.out.println("PIN tidak boleh berurutan");}
			return false;
		}
	}

	public static boolean allNumber(String str) { return str != null && str.matches("\\d+"); }
	public static boolean dup(String str) {
	    if (str == null || str.isEmpty()) return false;
	    
	    char pertama = str.charAt(0);
	    for (int i = 1; i < str.length(); i++) {
	        if (str.charAt(i) != pertama) {
	            return true; // Ada angka yang beda
	        }
	    }
	    return false;
	}
	public static boolean berurutan(String str) {
	    if (str == null || str.length() < 2) return false;

	    boolean isIncrease = true;
	    boolean isDecrease = true;

	    for (int i = 0; i < str.length() - 1; i++) {
	        int current = Character.getNumericValue(str.charAt(i));
	        int next = Character.getNumericValue(str.charAt(i + 1));

	        // Cek apakah urutan naik (misal: 1 ke 2)
	        if (next != current + 1) {
	            isIncrease = false;
	        }
	        // Cek apakah urutan turun (misal: 3 ke 2)
	        if (next != current - 1) {
	            isDecrease = false;
	        }
	    }

	    // Mengembalikan true jika salah satu kondisi terpenuhi
	    return isIncrease || isDecrease;
	}

	
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println("Setor tunai Rp" + df.format(nominal));
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public boolean tarikTunai(double nominal) {
		if (saldo < nominal) {
			System.out.println("Gagal: Saldo tidak cukup!");
			return false;
		} else if(saldo - 10000 < nominal) {
			System.out.println("Gagal: tolong sisakan Rp10,000.00!");
			return false;
		}  else {
			if (nominal >= 10000) {
				saldo -= nominal;
				String idTrx = "TRX-T-" + System.currentTimeMillis();
				Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
				riwayatTransaksi.add(trxBaru);
				
				System.out.println("Tarik tunai Rp" + df.format(nominal) );
				return true; //Transaksi sukses
			} else {
				System.out.println("Gagal: Nominal penarikan minimal Rp10,000.00!");
				return false;
			}	
		}
	}
	

	public void transfer(Rekening tujuan, double nominal, String pinInput) {
		// 1. Otentikasi PIN pengirim
		if (otentikasi(pinInput) == false) {
			System.out.println("Transfer dibatalkan!");
			return;
		}

		// Validasi rekening tujuan
		if (tujuan == null) {
    		System.out.println("Gagal: Rekening tujuan tidak ditemukan!");
    		return;
		}

		// 2. Tarik saldo dari rekening pengirim
		if (this.tarikTunai(nominal)) {
			// 3. Jika berhasil, setor ke rekening tujuan
			tujuan.setorTunai(nominal);

			System.out.println("Transfer berhasil!");
			System.out.println("Kepada: " + tujuan.getNamaPemilik());
			System.out.println("Nominal: Rp" + df.format(nominal));
		} else {
			System.out.println("Transfer gagal!");
		}
	}

	
	public void cetakMutasi() {
		for (Transaksi t : riwayatTransaksi) {
			t.cetakDetail();
		}
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + df.format(saldo));
		System.out.println("---------------------");
		
	}
}
