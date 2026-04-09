import java.util.*;

// Add-On Service Model
class ExtraService {
    private String serviceName;
    private double cost;

    public ExtraService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Manager for Add-On Services
class AddonServiceManager {

    // reservationId -> list of services
    private Map<String, List<ExtraService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, ExtraService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: "
                + service.getServiceName()
                + " to Reservation: " + reservationId);
    }

    // Display services for a reservation
    public void showServices(String reservationId) {

        List<ExtraService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservationId);
            return;
        }

        System.out.println("\nServices for " + reservationId + ":");

        for (ExtraService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getCost());
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<ExtraService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (ExtraService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class AddonServiceSystem {
    public static void main(String[] args) {

        AddonServiceManager manager = new AddonServiceManager();

        // Assume these reservation IDs already exist from booking system
        String res1 = "Single-1";
        String res2 = "Deluxe-2";

        // Guest selects services
        manager.addService(res1, new ExtraService("Breakfast", 500));
        manager.addService(res1, new ExtraService("Airport Pickup", 1200));

        manager.addService(res2, new ExtraService("Spa", 2000));

        // Display services
        manager.showServices(res1);
        manager.showServices(res2);

        // Cost calculation
        System.out.println("\nTotal Add-on Cost for " + res1 + ": ₹"
                + manager.calculateTotalCost(res1));

        System.out.println("Total Add-on Cost for " + res2 + ": ₹"
                + manager.calculateTotalCost(res2));
    }
}