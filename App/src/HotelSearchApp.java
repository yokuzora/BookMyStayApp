import java.util.*;

// Domain Model
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

// Inventory (RENAMED to avoid conflict)
class InventoryManager {
    private Map<String, Integer> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service (READ-ONLY)
class SearchService {
    private InventoryManager inventory;
    private Map<String, Room> roomData;

    public SearchService(InventoryManager inventory, Map<String, Room> roomData) {
        this.inventory = inventory;
        this.roomData = roomData;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:");

        for (String type : inventory.getRoomTypes()) {

            int count = inventory.getAvailability(type);

            // Filter only available rooms
            if (count > 0 && roomData.containsKey(type)) {

                Room room = roomData.get(type);

                System.out.println("---------------------");
                System.out.println("Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + count);
            }
        }
    }
}

// Main Class
public class HotelSearchApp {
    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Deluxe", 3);

        Map<String, Room> roomData = new HashMap<>();
        roomData.put("Single", new Room("Single", 2000, "AC, WiFi"));
        roomData.put("Double", new Room("Double", 3500, "AC, WiFi, TV"));
        roomData.put("Deluxe", new Room("Deluxe", 5000, "AC, WiFi, TV, Mini Bar"));

        SearchService search = new SearchService(inventory, roomData);
        search.searchAvailableRooms();
    }
}