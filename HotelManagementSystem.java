import java.util.*;

// 1. Room class (SRP: Handles only room details)
class Room {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        if (!isAvailable) {
            System.out.println("Room is already booked!");
        } else {
            isAvailable = false;
            System.out.println("Room " + roomNumber + " has been booked.");
        }
    }

    public void vacateRoom() {
        isAvailable = true;
        System.out.println("Room " + roomNumber + " is now available.");
    }
}

//2.  Customer class (SRP: Handles only customer details)
class Customer {
    private String name;
    private int age;
    private String contactNumber;

    public Customer(String name, int age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }
}

// 3. BookingManager class (SRP: Handles only booking operations)
class BookingManager {
    private List<Room> rooms;
    private Map<Integer, Customer> bookings;

    public BookingManager(int numRooms) {
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
        for (int i = 1; i <= numRooms; i++) {
            rooms.add(new Room(i));
        }
    }

    public void bookRoom(int roomNumber, Customer customer) {
        if (roomNumber < 1 || roomNumber > rooms.size()) {
            System.out.println("Invalid room number!");
            return;
        }
        Room room = rooms.get(roomNumber - 1);
        if (room.isAvailable()) {
            room.bookRoom();
            bookings.put(roomNumber, customer);
        } else {
            System.out.println("Room is already booked.");
        }
    }

    public void checkout(int roomNumber) {
        if (bookings.containsKey(roomNumber)) {
            rooms.get(roomNumber - 1).vacateRoom();
            bookings.remove(roomNumber);
            System.out.println("Checked out successfully!");
        } else {
            System.out.println("No booking found for this room.");
        }
    }

    // ✅ Display all room details
    public void displayRoomDetails() {
        System.out.println("\nRoom Details:");
        for (Room room : rooms) {
            String status = room.isAvailable() ? "Available" : "Booked";
            System.out.println("Room " + room.getRoomNumber() + ": " + status);
        }
    }

    // ✅ Display only available rooms
    public void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println("Room " + room.getRoomNumber());
            }
        }
    }
}

// 4. FoodOrder class (SRP: Handles food ordering)
class FoodOrder {
    private Map<String, Integer> orders;

    public FoodOrder() {
        orders = new HashMap<>();
    }

    public void addItem(String item, int quantity) {
        orders.put(item, orders.getOrDefault(item, 0) + quantity);
    }

    public void displayOrder() {
        System.out.println("Food Order Details:");
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            System.out.println(entry.getKey() + " x " + entry.getValue());
        }
    }
}

//5.  Billing class (SRP: Handles only billing operations)
class Billing {
    public static void generateBill(Customer customer, int roomCharge, FoodOrder foodOrder) {
        System.out.println("Generating bill for " + customer.getName());
        System.out.println("Room Charges: " + roomCharge);
        foodOrder.displayOrder();
        System.out.println("Total amount due: " + (roomCharge + 500)); // Example calculation
    }
}

// 6. Main class (SRP: Handles user interaction and program execution)
public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingManager bookingManager = new BookingManager(10);
        FoodOrder foodOrder = new FoodOrder();

        while (true) {
            System.out.println("\n1. Book Room\n2. Order Food\n3. Checkout\n4. Display All Room Details\n5. Display Available Rooms\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter name, age, contact number:");
                    String name = scanner.next();
                    int age = scanner.nextInt();
                    String contact = scanner.next();
                    Customer customer = new Customer(name, age, contact);
                    System.out.println("Enter room number:");
                    int roomNumber = scanner.nextInt();
                    bookingManager.bookRoom(roomNumber, customer);
                    break;
                case 2:
                    System.out.println("Enter food item and quantity:");
                    String item = scanner.next();
                    int quantity = scanner.nextInt();
                    foodOrder.addItem(item, quantity);
                    break;
                case 3:
                    System.out.println("Enter room number for checkout:");
                    int checkoutRoom = scanner.nextInt();
                    bookingManager.checkout(checkoutRoom);
                    break;
                case 4:
                    bookingManager.displayRoomDetails();
                    break;
                case 5:
                    bookingManager.displayAvailableRooms();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
