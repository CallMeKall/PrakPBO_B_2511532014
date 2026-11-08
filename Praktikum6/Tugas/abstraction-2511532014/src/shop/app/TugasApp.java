package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {

    public static void main(String[] args) {

        System.out.println("Tugas oleh " + StudentInfo.NAME
                + " (" + StudentInfo.NIM + ")");

        // T1: GRAND TOTAL DENGAN BIAYA ADMIN
        Product coffee = new PhysicalProduct(
                "KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);

        Product rendang = new PhysicalProduct(
                "RDG-002", "Rendang Kemasan 250g", 120_000, 400);

        Product ebook = new DigitalProduct(
                "EBK-003", "E-book Resep Kue Nusantara",
                50_000, "https://toko-umkm.example/unduh/ebk-003");

        List<Product> cart = List.of(coffee, rendang, ebook);

        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        EWalletPayment wallet = new EWalletPayment(
                "DompetKu", 500_000);

        BankTransferPayment transfer = new BankTransferPayment(
                "Bank Nagari");

        CreditCardPayment card = new CreditCardPayment(
                "Siti Rahma", 1_000_000);

        System.out.println("\n== T1: Total Pembayaran Ekspres ==");

        System.out.println(wallet.getName()
                + " : Rp" + checkout.grandTotal(cart, express, wallet));

        System.out.println(transfer.getName()
                + " : Rp" + checkout.grandTotal(cart, express, transfer));

        System.out.println(card.getName()
                + " : Rp" + checkout.grandTotal(cart, express, card));

        System.out.println("\n== Kebijakan Pembayaran ==");

        System.out.println("Kartu Kredit : " + card.policy());
        System.out.println("E-Wallet     : " + wallet.policy());

        // T2: SISTEM NOTIFIKASI
        System.out.println("\n== T2: Sistem Notifikasi ==");

        NotificationService notificationService =
                new NotificationService();

        // Mendaftarkan tiga channel
        notificationService.register(new EmailChannel());

        notificationService.register(
                new SmsChannel(StudentInfo.SMS_MAX_LENGTH));

        notificationService.register(new WhatsAppChannel());

        // Anonymous class untuk channel LOG
        notificationService.register(new NotificationChannel() {

            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] "
                        + contact.getName() + ": " + message);

                return true;
            }
        });

        // Dua kontak pelanggan
        CustomerContact siti = new CustomerContact(
                "Siti Rahma",
                "siti@example.com",
                "081234567890");

        CustomerContact budi = new CustomerContact(
                "Budi",
                null,
                "+6281298765432");

        // Pesan lebih dari 200 karakter
        String message =
                "Pesanan ORD-0001 sudah dikirim menggunakan kurir "
                + "Ekspres. Nomor resi pengiriman dapat diperiksa "
                + "melalui aplikasi toko kami dalam waktu 1x24 jam. "
                + "Terima kasih telah berbelanja produk UMKM "
                + "Sumatera Barat. Kami berharap produk yang Anda "
                + "pesan sampai dengan aman dan sesuai harapan. "
                + "Silakan hubungi layanan pelanggan apabila "
                + "terdapat kendala selama proses pengiriman.";

        System.out.println("\n-- Notifikasi untuk Siti --");

        int sitiSuccess = notificationService.broadcast(siti, message);

        System.out.println("Berhasil dikirim: "
                + sitiSuccess + " channel");

        System.out.println("\n-- Notifikasi untuk Budi --");

        int budiSuccess = notificationService.broadcast(budi, message);

        System.out.println("Berhasil dikirim: "
                + budiSuccess + " channel");
    }
}