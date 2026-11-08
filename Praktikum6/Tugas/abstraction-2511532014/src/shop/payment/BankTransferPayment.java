package shop.payment;

public class BankTransferPayment implements shop.payment.PaymentMethod {
    private final String bankName;

    public BankTransferPayment(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String getName() { return "Transfer " + bankName;}

    @Override
    public boolean pay(long amount) {
        if (amount > 0) {
            return true;
        }
        return false;
    }


    @Override
    public long adminFee(long amount) {
        return shop.StudentInfo.TRANSFER_ADMIN_FEE;
    }
}

