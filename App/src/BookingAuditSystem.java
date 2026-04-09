import java.util.*;

// Booking Record (RENAMED)
class BookingRecord {
    private String bookingId;
    private String guestName;
    private String roomType;

    public BookingRecord(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// History Manager (RENAMED)
class BookingHistoryStore {

    // Maintains insertion order
    private List<BookingRecord> records = new ArrayList<>();

    // Store confirmed booking
    public void addRecord(BookingRecord record) {
        records.add(record);
    }

    // Retrieve all records (read-only usage expected)
    public List<BookingRecord> getAllRecords() {
        return records;
    }
}

// Report Service (READ-ONLY)
class BookingAnalyticsService {

    private BookingHistoryStore historyStore;

    public BookingAnalyticsService(BookingHistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    // Display all bookings
    public void displayAllRecords() {

        System.out.println("\nBooking History:");

        for (BookingRecord r : historyStore.getAllRecords()) {
            System.out.println("----------------------");
            System.out.println("Booking ID: " + r.getBookingId());
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());
        }
    }

    // Generate summary report
    public void generateReport() {

        Map<String, Integer> summary = new HashMap<>();

        for (BookingRecord r : historyStore.getAllRecords()) {
            String type = r.getRoomType();
            summary.put(type, summary.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nSummary Report:");

        for (String type : summary.keySet()) {
            System.out.println(type + " bookings: " + summary.get(type));
        }
    }
}

// Main Class
public class BookingAuditSystem {
    public static void main(String[] args) {

        BookingHistoryStore history = new BookingHistoryStore();

        // Simulate confirmed bookings
        history.addRecord(new BookingRecord("R101", "Alice", "Single"));
        history.addRecord(new BookingRecord("R102", "Bob", "Single"));
        history.addRecord(new BookingRecord("R103", "Charlie", "Deluxe"));

        // Admin view
        BookingAnalyticsService report = new BookingAnalyticsService(history);

        report.displayAllRecords();
        report.generateReport();
    }
}