import java.util.*;

// Reservation (Represents booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Request Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: "
                + reservation.getGuestName() + " -> "
                + reservation.getRoomType());
    }

    // View all requests (no removal)
    public void displayQueue() {
        System.out.println("\nBooking Queue (FIFO Order):");

        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " -> " + r.getRoomType());
        }
    }
}

// Main Class
public class BookingQueueApp {
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Guests submit requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Deluxe"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));

        // Display queue (order preserved)
        bookingQueue.displayQueue();
    }
}