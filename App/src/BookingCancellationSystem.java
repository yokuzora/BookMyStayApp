import java.util.*;

// Booking Record
class ActiveBooking {
    private String bookingId;
    private String roomType;

    public ActiveBooking(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Manager
class CancellationInventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Booking Store (tracks active bookings)
class ActiveBookingStore {
    private Map<String, ActiveBooking> bookings = new HashMap<>();

    public void addBooking(ActiveBooking booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    public ActiveBooking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public void removeBooking(String bookingId) {
        bookings.remove(bookingId);
    }

    public boolean exists(String bookingId) {
        return bookings.containsKey(bookingId);
    }
}

// Cancellation Service (Rollback using Stack)
class CancellationService {

    private ActiveBookingStore bookingStore;
    private CancellationInventoryManager inventory;

    // Stack for rollback tracking (LIFO)
    private Stack<String> releasedRooms = new Stack<>();

    public CancellationService(ActiveBookingStore bookingStore,
                               CancellationInventoryManager inventory) {
        this.bookingStore = bookingStore;
        this.inventory = inventory;
    }

    public void cancelBooking(String bookingId) {

        System.out.println("\nCancelling: " + bookingId);

        // Validation
        if (!bookingStore.exists(bookingId)) {
            System.out.println("Cancellation Failed: Booking does not exist");
            return;
        }

        // Get booking
        ActiveBooking booking = bookingStore.getBooking(bookingId);

        // Record rollback (push to stack)
        releasedRooms.push(bookingId);

        // Restore inventory
        inventory.increaseRoom(booking.getRoomType());

        // Remove booking
        bookingStore.removeBooking(bookingId);

        System.out.println("Cancellation Successful for " + bookingId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack:");
        for (String id : releasedRooms) {
            System.out.println(id);
        }
    }
}

// Main Class
public class BookingCancellationSystem {
    public static void main(String[] args) {

        // Setup inventory
        CancellationInventoryManager inventory = new CancellationInventoryManager();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Deluxe", 0);

        // Active bookings
        ActiveBookingStore store = new ActiveBookingStore();
        store.addBooking(new ActiveBooking("Single-1", "Single"));
        store.addBooking(new ActiveBooking("Deluxe-2", "Deluxe"));

        // Cancellation service
        CancellationService service = new CancellationService(store, inventory);

        // Perform cancellations
        service.cancelBooking("Single-1");   // valid
        service.cancelBooking("Single-1");   // invalid (already cancelled)
        service.cancelBooking("XYZ-9");      // invalid

        // Show results
        inventory.displayInventory();
        service.showRollbackStack();
    }
}