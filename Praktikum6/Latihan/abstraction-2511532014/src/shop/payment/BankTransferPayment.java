package shop.payment;

public class BankTransferPayment implements shop.payment.PaymentMethod {
    private final String bankName;

    public BankTransferPayment(String bankName) {
        this.bankName = bankName;
    }

    public String getName() { return "Transfer " + bankName;}
    public boolean pay(long amount) {
        if (amount > 0) {
            return true;
        }
        return false;
    }
}
