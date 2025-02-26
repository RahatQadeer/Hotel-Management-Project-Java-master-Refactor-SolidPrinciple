# Hotel Management System

## 📌 Overview
This project is a **Hotel Management System** written in **Java**. It allows users to **book rooms, order food, checkout, and view room details**.  

The original code was obtained from the following GitHub repository:  
**[GitHub Repository Link](https://github.com/shouryaj98/Hotel-Management-Project-Java/blob/master/Main.java)**  

After obtaining the code, **SOLID principles** were applied to improve structure, readability, and maintainability.

## ✅ **Applied SOLID Principles**
1. **Single Responsibility Principle (SRP)**  
   - Separated concerns into different classes:
     - `Room` → Handles room details  
     - `Customer` → Manages customer information  
     - `BookingManager` → Manages room bookings  
     - `FoodOrder` → Handles food ordering  
     - `Billing` → Processes bills  
     - `HotelManagementSystem` → Manages user interaction  

2. **Open/Closed Principle (OCP)**  
   - Code is **open for extension but closed for modification**  
   - Example: New room types or pricing structures can be added without modifying existing code  

3. **Liskov Substitution Principle (LSP)**  
   - Currently, there's no subclassing, but if extended (e.g., `LuxuryRoom extends Room`), the derived class will work without breaking functionality  

4. **Interface Segregation Principle (ISP)**  
   - Not many interfaces needed in this small project, but if expanded, **separate interfaces** can be created (e.g., `IBooking`, `IFoodOrder`)  

5. **Dependency Inversion Principle (DIP)**  
   - High-level modules (e.g., `HotelManagementSystem`) do not depend on low-level modules but rely on abstractions  

---

## ⚡ Features
- **Book a Room** 🏨  
- **Order Food** 🍔  
- **Checkout & Generate Bill** 🧾  
- **Display All Room Details** 🏠  
- **Display Available Rooms** ✅  

