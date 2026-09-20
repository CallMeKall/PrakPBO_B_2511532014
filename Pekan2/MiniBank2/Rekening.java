package MiniBank2;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Rekening {
	
	DecimalFormat df = new DecimalFormat("###,###.00");

	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	ArrayList<Transaksi> riwayatTransaksi;

	public Rekening(String nomor, String nama, double saldoAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		
		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + df.format(saldo));
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
	
	public void tarikTunai(double nominal) {
		if (saldo < nominal) {
			System.out.println("Gagal: Saldo tidak cukup!");
		} else if(saldo - 10000 < nominal) {
			System.out.println("Gagal: tolong sisakan Rp10,000.00!");
		}  else {
			if (nominal >= 10000) {
				saldo -= nominal;
				String idTrx = "TRX-T-" + System.currentTimeMillis();
				Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
				riwayatTransaksi.add(trxBaru);
				
				System.out.println("Tarik tunai Rp" + df.format(nominal) );
			} else {
				System.out.println("Gagal: Nominal penarikan minimal Rp10,000.00!");
			}
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
