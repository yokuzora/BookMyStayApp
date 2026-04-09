import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor: initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with initial count
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase/decrease)
    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            int updated = current + change;

            if (updated >= 0) {
                inventory.put(roomType, updated);
            } else {
                System.out.println("Not enough rooms available");
            }
        } else {
            System.out.println("Room type not found");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}