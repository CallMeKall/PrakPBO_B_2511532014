package review.fixed;

import java.util.ArrayList;
import java.util.List;

//pemisahan interface untuk setiap operasi yang berbeda
interface Payable {
    boolean pay(long amount);
}

interface Refundable {
    boolean refund(long amount);
}

interface Shippable {
    long ship(int weightGrams);
}

interface InvoicePrintable {
    String printInvoice();
}

class GiftVoucher implements Payable, InvoicePrintable {

    private long balance = 100_000;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "sisa Rp" + balance;
    }
}

abstract class Courier {

    public abstract long cost(int kilograms);
}

class ExpressCourier extends Courier {

    @Override
    public long cost(int kilograms) {
        if (kilograms <= 0) {
            return 0;
        }

        return kilograms * 18_000L;
    }
}

class RegularCourier extends Courier {

    @Override
    public long cost(int kilograms) {
        if (kilograms <= 0) {
            return 0;
        }

        return kilograms * 10_000L;
    }
}

class SameDayCourier extends Courier {

    @Override
    public long cost(int kilograms) {
        if (kilograms <= 0) {
            return 0;
        }

        return kilograms * 30_000L;
    }
}

class Cart {

    private final List<Item> items = new ArrayList<>();

    static class Item {

        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;

        for (Item item : items) {
            total += item.subtotal();
        }

        return total;
    }
}

final class Address {

    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

final class SimpleOrder {

    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

interface Discount {

    long apply(long price);
}

class MemberDiscount implements Discount {

    @Override
    public long apply(long price) {
        return price - price * 5 / 100;
    }
}

class FlashSaleDiscount implements Discount {

    @Override
    public long apply(long price) {
        return price - price * 30 / 100;
    }
}

public class BadShopRefactored {

    public static void main(String[] args) {

        // Cart
        Cart cart = new Cart();

        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));

        System.out.println("Total cart: " + cart.total());

        // Courier
        Courier courier = new ExpressCourier();

        System.out.println("Ongkir Ekspres 2 kg: "
                + courier.cost(2));

        // Address dan Order
        Address firstAddress = new Address(
                "Jl. Sudirman 10", "Padang");

        Address secondAddress = new Address(
                "Jl. Sudirman 10", "Bukittinggi");

        SimpleOrder first = new SimpleOrder(
                "ORD-1", firstAddress);

        SimpleOrder second = new SimpleOrder(
                "ORD-2", secondAddress);

        System.out.println(first);
        System.out.println(second);

        // Discount
        Discount flashSale = new FlashSaleDiscount();

        System.out.println("Harga flash sale: "
                + flashSale.apply(100_000));

        // Voucher
        Payable voucher = new GiftVoucher();

        InvoicePrintable invoice = (InvoicePrintable) voucher;

        System.out.println(voucher.pay(30_000)
                + " | " + invoice.printInvoice());
    }
}