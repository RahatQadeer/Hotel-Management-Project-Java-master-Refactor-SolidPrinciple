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

// Abstract class for room booking to follow OCP
abstract class RoomBooking {
    public abstract void bookRoom(Room room, Customer customer);
}


class StandardRoomBooking extends RoomBooking {
    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable()) {
            room.bookRoom();
            System.out.println("Booking confirmed for " + customer.getName());
        } else {
            System.out.println("Room is already booked.");
        }
    }
}


class VIPRoomBooking extends RoomBooking {
    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable()) {
            room.bookRoom();
            System.out.println("VIP booking confirmed for " + customer.getName());
        } else {
            System.out.println("VIP Room is already booked.");
        }
    }
}

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

class BookingManager {
    private List<Room> rooms;
    private Map<Integer, Customer> bookings;
    private RoomBooking roomBookingStrategy; // Applying OCP

    public BookingManager(int numRooms, RoomBooking bookingStrategy) {
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
        this.roomBookingStrategy = bookingStrategy; 
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
        roomBookingStrategy.bookRoom(room, customer); 
        bookings.put(roomNumber, customer);
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
}

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


class Billing {
    public static void generateBill(Customer customer, int roomCharge, FoodOrder foodOrder) {
        System.out.println("Generating bill for " + customer.getName());
        System.out.println("Room Charges: " + roomCharge);
        foodOrder.displayOrder();
        System.out.println("Total amount due: " + (roomCharge + 500)); // Example calculation
    }
}

// Main class
public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingManager bookingManager = new BookingManager(10, new StandardRoomBooking()); // Strategy injected
        FoodOrder foodOrder = new FoodOrder();

        while (true) {
            System.out.println("\n1. Book Room\n2. Order Food\n3. Checkout\n4. Exit");
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
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
