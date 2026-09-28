package MiniBank3;

public class Transaksi {
	private String idTransaksi;
	private String jenis;
	private double nominal;
	
	//konstruktor
	public Transaksi(String id, String jenis, double nominal) {
		this.idTransaksi = id;
		this.jenis = jenis;
		this.nominal = nominal;
	}
	
	//getter
	public String getTransaksi() {return idTransaksi;}
	public String geJenis() {return jenis;}
	public double getNominal() {return nominal;}
	
	public void cetakDetail() {
		System.out.println("ID: " + idTransaksi + " | Jenis: " + jenis + " | Nominal: Rp" + nominal);
	}
}
