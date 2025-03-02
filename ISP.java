/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isp;
import java.util.*;

// RoomOperations Interface - Ensures ISP by segregating responsibilities
interface RoomOperations {
    void bookRoom(Customer customer);
    boolean isAvailable();
}

// Room class (SRP: Handles only room details)
class Room implements RoomOperations {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void bookRoom(Customer customer) {
        if (!isAvailable) {
            System.out.println("Room is already booked!");
        } else {
            isAvailable = false;
            System.out.println("Room " + roomNumber + " has been booked for " + customer.getName());
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

// VIP rooms can only be booked by VIP customers
class OnlyVIPAllowed implements BookingEligibility {
    @Override
    public boolean isEligible(Customer customer) {
        return customer.getName().startsWith("VIP");
    }
}

// RoomBooking Interface - Ensures ISP by separating booking concerns
interface RoomBooking {
    void bookRoom(Room room, Customer customer);
}

// Standard Room Booking
class StandardRoomBooking implements RoomBooking {
    private BookingEligibility eligibility = new AllCustomersAllowed();

    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable() && eligibility.isEligible(customer)) {
            room.bookRoom(customer);
        } else {
            System.out.println("Standard room is unavailable.");
        }
    }
}

// VIP Room Booking
class VIPRoomBooking implements RoomBooking {
    private BookingEligibility eligibility = new OnlyVIPAllowed();

    @Override
    public void bookRoom(Room room, Customer customer) {
        if (room.isAvailable() && eligibility.isEligible(customer)) {
            room.bookRoom(customer);
        } else {
            System.out.println("VIP Room can only be booked by VIP customers.");
        }
    }
}

// FoodOrderable Interface - Segregates food ordering logic
interface FoodOrderable {
    void addItem(String item, int quantity);
    void displayOrder();
}

// Food Order class implementing FoodOrderable
class FoodOrder implements FoodOrderable {
    private Map<String, Integer> orders = new HashMap<>();

    @Override
    public void addItem(String item, int quantity) {
        orders.put(item, orders.getOrDefault(item, 0) + quantity);
    }

    @Override
    public void displayOrder() {
        System.out.println("Food Order Details:");
        orders.forEach((item, quantity) -> System.out.println(item + " x " + quantity));
    }
}

// Billable Interface - Ensures billing logic is separate
interface Billable {
    void generateBill(Customer customer, int roomCharge, FoodOrderable foodOrder);
}

// Billing class implementing Billable
class Billing implements Billable {
    @Override
    public void generateBill(Customer customer, int roomCharge, FoodOrderable foodOrder) {
        System.out.println("Generating bill for " + customer.getName());
        System.out.println("Room Charges: " + roomCharge);
        foodOrder.displayOrder();
        System.out.println("Total amount due: " + (roomCharge + 500)); // Example calculation
    }
}

/**
 *
 * @author ML
 */
public class ISP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        Room standardRoom = new Room(1);
        Room vipRoom = new Room(2);
        RoomBooking standardBooking = new StandardRoomBooking();
        RoomBooking vipBooking = new VIPRoomBooking();
        FoodOrderable foodOrder = new FoodOrder();
        Billable billing = new Billing();

        while (true) {
            System.out.println("\n1. Book Standard Room\n2. Book VIP Room\n3. Order Food\n4. Checkout\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine();
                    Customer customer = new Customer(scanner.nextLine(), scanner.nextInt(), scanner.next());
                    standardBooking.bookRoom(standardRoom, customer);
                    break;

                case 2:
                    System.out.println("Enter name, age, contact number:");
                    scanner.nextLine();
                    Customer vipCustomer = new Customer(scanner.nextLine(), scanner.nextInt(), scanner.next());
                    vipBooking.bookRoom(vipRoom, vipCustomer);
                    break;

                case 3:
                    System.out.println("Enter food item and quantity:");
                    foodOrder.addItem(scanner.next(), scanner.nextInt());
                    break;

                case 4:
                    billing.generateBill(new Customer("John Doe", 30, "1234567890"), 1000, foodOrder);
                    standardRoom.vacateRoom();
                    vipRoom.vacateRoom();
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
