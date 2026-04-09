import java.util.*;

// Custom Exception (Domain-specific)
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking Request
class BookingInput {
    private String guestName;
    private String roomType;

    public BookingInput(String guestName, String roomType) {
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

// Inventory (Safe State Management)
class SafeInventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int current = inventory.getOrDefault(type, -1);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.put(type, current - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// Validator (Fail-Fast)
class BookingValidator {

    public void validate(BookingInput input, SafeInventoryManager inventory)
            throws InvalidBookingException {

        if (input.getGuestName() == null || input.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (input.getRoomType() == null || input.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!inventory.isValidRoomType(input.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + input.getRoomType());
        }

        if (inventory.getAvailability(input.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + input.getRoomType());
        }
    }
}

// Booking Processor (Handles Errors Gracefully)
class SafeBookingProcessor {

    private SafeInventoryManager inventory;
    private BookingValidator validator;

    public SafeBookingProcessor(SafeInventoryManager inventory) {
        this.inventory = inventory;
        this.validator = new BookingValidator();
    }

    public void processBooking(BookingInput input) {

        try {
            // Validation (fail-fast)
            validator.validate(input, inventory);

            // Safe allocation
            inventory.reduceRoom(input.getRoomType());

            System.out.println("Booking Successful for " + input.getGuestName() +
                    " (" + input.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BookingValidationSystem {
    public static void main(String[] args) {

        SafeInventoryManager inventory = new SafeInventoryManager();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Deluxe", 0);

        SafeBookingProcessor processor = new SafeBookingProcessor(inventory);

        // Valid booking
        processor.processBooking(new BookingInput("Alice", "Single"));

        // Invalid: no availability
        processor.processBooking(new BookingInput("Bob", "Single"));

        // Invalid: wrong room type
        processor.processBooking(new BookingInput("Charlie", "Suite"));

        // Invalid: empty name
        processor.processBooking(new BookingInput("", "Single"));
    }
}