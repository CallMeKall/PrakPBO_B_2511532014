package MiniBank5;

public class RekeningVIP extends Rekening {
	

	public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
		super(nomor, nama, saldoAwal +  100000, pinAwal);
	}

}
