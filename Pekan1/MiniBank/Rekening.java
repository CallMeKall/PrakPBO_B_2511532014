package MiniBank;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;

	public Rekening(String nomor, String nama, double saldoAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			System.out.println("Setor tunai Rp" + nominal);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public void tarikTunai(double nominal) {
		if (saldo < nominal) {
			System.out.println("Gagal: Saldo tidak cukup!");
		} else if(saldo - 10000 < nominal) {
			System.out.println("Gagal: tolong sisakan 10000!");
		}  else {
			if (nominal >= 10000) {
				saldo -= nominal;
				System.out.println("Tarik tunai Rp" + nominal);
			} else {
				System.out.println("Gagal: Nominal penarikan minimal Rp10.000!");
			}
		}
		
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + saldo);
		System.out.println("---------------------");
		
	}
}
