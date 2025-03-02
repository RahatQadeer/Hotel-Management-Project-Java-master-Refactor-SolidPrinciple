package lsp;

import java.util.*;

// Room class (SRP: Handles only room details)
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

// Customer class
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

// Booking Eligibility Interface (LSP Applied)
interface BookingEligibility {
    boolean isEligible(Customer customer);
}

// Standard rooms can be booked by anyone
class AllCustomersAllowed implements BookingEligibility {
    @Override
    public boolean isEligible(Customer customer) {
        return true;
    }
}

// VIP rooms can only  booked by VIP customers
class OnlyVIPAllowed implements BookingEligibility {
    @Override
    public boolean isEligible(Customer customer) {
        return customer.getName().startsWith("VIP");
    }
}

// Abstract class for room booking (OCP applied)
abstract class RoomBooking {
    protected BookingEligibility eligibility;

    public RoomBooking(BookingEligibility eligibility) {
        this.eligibility = eligibility;
    }

    public abstract void bookRoom(Room room, Customer customer);
}

// Standard Room Booking (Follows LSP)
class StandardRoomBooking extends RoomBooking {
    public StandardRoomBooking() {
        super(new AllCustomersAllowed()); 
    }

    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable() && eligibility.isEligible(customer)) {
            room.bookRoom();
            System.out.println("Booking confirmed for " + customer.getName());
        } else {
            System.out.println("Room is already booked.");
        }
    }
}

// VIP Room Booking (Follows LSP)
class VIPRoomBooking extends RoomBooking {
    public VIPRoomBooking() {
        super(new OnlyVIPAllowed()); 
    }

    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable() && eligibility.isEligible(customer)) {
            room.bookRoom();
            System.out.println("VIP booking confirmed for " + customer.getName());
        } else {
            System.out.println("VIP Room can only be booked by VIP customers.");
        }
    }
}

// Booking Manager (Handles Room Management)
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

// Food Order class
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

// Billing class
class Billing {
    public static void generateBill(Customer customer, int roomCharge, FoodOrder foodOrder) {
        System.out.println("Generating bill for " + customer.getName());
        System.out.println("Room Charges: " + roomCharge);
        foodOrder.displayOrder();
        System.out.println("Total amount due: " + (roomCharge + 500)); // Example calculation
    }
}

// Main class
public class LSP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingManager standardBookingManager = new BookingManager(10, new StandardRoomBooking());
        BookingManager vipBookingManager = new BookingManager(5, new VIPRoomBooking());
        FoodOrder foodOrder = new FoodOrder();

        while (true) {
            System.out.println("\n1. Book Standard Room\n2. Book VIP Room\n3. Order Food\n4. Checkout\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine(); 
                    String name = scanner.nextLine(); 
                    int age = scanner.nextInt();
                    scanner.nextLine(); 
                    String contact = scanner.nextLine();

                    Customer customer = new Customer(name, age, contact);
                    System.out.println("Enter room number:");
                    int roomNumber = scanner.nextInt();
                    standardBookingManager.bookRoom(roomNumber, customer);
                    break;


                case 2:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine(); 
                    String vipName = scanner.nextLine(); 
                    int vipAge = scanner.nextInt();
                    scanner.nextLine(); 
                    String vipContact = scanner.nextLine();

                    Customer vipCustomer = new Customer(vipName, vipAge, vipContact);
                    System.out.println("Enter VIP room number:");
                    int vipRoomNumber = scanner.nextInt();
                    vipBookingManager.bookRoom(vipRoomNumber, vipCustomer);
                    break;


                case 3:
                    System.out.println("Enter food item and quantity:");
                    String item = scanner.next();
                    int quantity = scanner.nextInt();
                    foodOrder.addItem(item, quantity);
                    break;

                case 4:
                    System.out.println("Enter room number for checkout:");
                    int checkoutRoom = scanner.nextInt();
                    standardBookingManager.checkout(checkoutRoom);
                    vipBookingManager.checkout(checkoutRoom);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
