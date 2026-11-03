package shop.payment;

public class EWalletPayment implements RefundablePayment {
    private final String provider;
    private long balance;

    public EWalletPayment(String provider, long balance) {
        this.provider = provider;
        this.balance = balance;
    }

    public String getName() { return "E-Wallet " + provider;}
    public long getBalance() { return balance; }
    public boolean pay(long amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean refund(long amount){
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }
}
