# Open/Closed Principle (OCP) Implementation in Hotel Management System

## Overview
This project applies the **Open/Closed Principle (OCP)** to improve the design and maintainability of a Java-based Hotel Management System. By adhering to OCP, the system is designed to be **extensible without modifying existing code**, ensuring scalability and flexibility.

## Key Issues in the Original Code
- Hardcoded booking rules made it difficult to introduce new room types.
- The billing system had fixed pricing, requiring modifications for new pricing models.
- The food ordering system was separate, leading to inconsistent billing.

## Refactoring Approach
To address these issues, we applied **abstraction and strategy patterns**:
1. **Booking Strategy:**
   - Introduced an abstract `RoomBooking` class.
   - Implemented `StandardRoomBooking` and `VIPRoomBooking` as separate strategies.
   - Allows seamless addition of new booking types (e.g., `DeluxeRoomBooking`).

2. **Billing Strategy:**
   - Created an interface `BillingStrategy` to support multiple pricing models.
   - Enables different pricing rules (e.g., VIP discounts) without modifying core billing logic.

3. **Dynamic Strategy Selection:**
   - Modified `BookingManager` to accept different booking and billing strategies at runtime.

4. **Integrated Food Ordering and Billing:**
   - Unified food ordering and billing to ensure a comprehensive customer bill.

## Benefits of OCP Implementation
- **Extensibility:** New features (e.g., custom booking policies) can be added without altering existing code.
- **Flexibility:** Hotels can define and switch booking and billing strategies dynamically.
- **Improved Maintainability:** Codebase remains modular, reducing dependencies and simplifying modifications.

By following OCP, the system is now more adaptable, reducing the risk of introducing errors when adding new functionality. This enhances long-term scalability and code maintainability.



