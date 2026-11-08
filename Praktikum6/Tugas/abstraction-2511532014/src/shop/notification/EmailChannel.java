package shop.notification;

public class EmailChannel extends AbstractChannel {

    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String email = contact.getEmail();

        if (email != null && email.contains("@")) {
            return email;
        }

        return null;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[EMAIL to " + recipient + "] " + message);
        return true;
    }
}
