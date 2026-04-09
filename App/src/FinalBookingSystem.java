import java.util.*;

// Request Model (RENAMED)
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Queue Manager (RENAMED)
class RequestQueueManager {
    private Queue<BookingRequest> requestQueue = new LinkedList<>();

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    public BookingRequest getNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

// Inventory (RENAMED)
class InventoryManagerV2 {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Processor (RENAMED)
class BookingProcessorEngine {

    private InventoryManagerV2 inventory;

    private Set<String> assignedRoomIds = new HashSet<>();
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    private int counter = 1;

    public BookingProcessorEngine(InventoryManagerV2 inventory) {
        this.inventory = inventory;
    }

    public void processRequests(RequestQueueManager queue) {

        while (!queue.isEmpty()) {

            BookingRequest request = queue.getNextRequest();
            String type = request.getRoomType();

            System.out.println("\nProcessing: " + request.getGuestName());

            if (inventory.getAvailability(type) > 0) {

                String roomId = type + "-" + counter++;

                if (!assignedRoomIds.contains(roomId)) {

                    assignedRoomIds.add(roomId);

                    allocationMap
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(roomId);

                    inventory.reduceRoom(type);

                    System.out.println("Booking Confirmed");
                    System.out.println("Guest: " + request.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);

                } else {
                    System.out.println("Error: Duplicate ID");
                }

            } else {
                System.out.println("Booking Failed - No Availability");
            }
        }
    }
}

// Main Class
public class FinalBookingSystem {
    public static void main(String[] args) {

        InventoryManagerV2 inventory = new InventoryManagerV2();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Deluxe", 1);

        RequestQueueManager queue = new RequestQueueManager();
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Single")); // fail
        queue.addRequest(new BookingRequest("David", "Deluxe"));

        BookingProcessorEngine processor = new BookingProcessorEngine(inventory);
        processor.processRequests(queue);
    }
}