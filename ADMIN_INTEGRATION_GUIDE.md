# Admin Dashboard Integration Guide

## Quick Start Integration

### Step 1: Update LoginController to Route to Admin Dashboard

Modify the `handleLogin()` method in LoginController.java to check user role:

```java
@FXML
public void handleLogin() {
    String email = emailField.getText().trim();
    String password = passwordField.getText().trim();
    
    User user = userDAO.login(email, password);
    
    if(user != null) {
        try {
            String fxmlFile;
            // Check if admin
            if (user.getRole() != null && user.getRole().equals("ADMIN")) {
                fxmlFile = "/view/admin-dashboard.fxml";
            } else {
                fxmlFile = "/view/dashboard.fxml"; // Customer dashboard
            }
            
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlFile)
            );
            
            Scene scene = new Scene(loader.load());
            
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading dashboard");
        }
    } else {
        showError("Invalid Email or Password");
    }
}

private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Login Error");
    alert.setContentText(message);
    alert.showAndWait();
}
```

### Step 2: Update User Model (If Needed)

Make sure the User.java model has a `role` field:

```java
public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role;        // Add this
    private String customerType;
    private Timestamp createdAt;
    
    // Getters and setters for role
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
```

### Step 3: Database Setup

Ensure your users table has a role column:

```sql
ALTER TABLE users ADD COLUMN role VARCHAR(50) DEFAULT 'CUSTOMER';
-- OR if creating the table:
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(50) DEFAULT 'CUSTOMER',
    customer_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Step 4: Create Test Admin User

```sql
INSERT INTO users (full_name, email, password, phone, role, customer_type) 
VALUES ('Admin User', 'admin@example.com', 'admin123', '1234567890', 'ADMIN', 'PREMIUM');
```

### Step 5: Compile and Run

```bash
mvn clean compile
mvn javafx:run
```

## File Structure After Integration

```
Cinema_management/
├── src/
│   └── main/
│       ├── java/com/example/dp/
│       │   ├── controller/
│       │   │   ├── AdminDashboardController.java (NEW)
│       │   │   ├── AdminMovieDialogController.java (NEW)
│       │   │   ├── LoginController.java (MODIFIED)
│       │   │   └── ... (other controllers)
│       │   └── dao/
│       │       ├── BookingDAO.java (EXTENDED)
│       │       ├── PaymentDAO.java (NEW)
│       │       ├── MovieDAO.java (EXTENDED)
│       │       └── ... (other DAOs)
│       └── resources/
│           ├── view/
│           │   ├── admin-dashboard.fxml (NEW)
│           │   ├── login.fxml (existing)
│           │   └── ... (other FXMLs)
│           └── css/
│               └── style.css (existing - includes admin styles)
├── ADMIN_DASHBOARD_IMPLEMENTATION.md (NEW)
└── ADMIN_INTEGRATION_GUIDE.md (THIS FILE)
```

## Testing the Integration

### Test Case 1: Admin Login
1. Login with admin credentials (admin@example.com / admin123)
2. Should redirect to admin dashboard screen
3. Verify all 4 sections (Overview, Movies, Bookings, Payments) are visible

### Test Case 2: Overview Statistics
1. In Overview section, verify:
   - Total Bookings count is correct
   - Total Revenue is calculated from PAID payments
   - Active Movies count shows NOW_SHOWING movies
   - Pending Bookings count shows PENDING status bookings

### Test Case 3: Movie Management
1. Click "Movie Management" in sidebar
2. Table should load all movies from database
3. Test "Add Movie" button (opens dialog)
4. Test "Edit Selected" button (populate form)
5. Test "Delete Selected" button

### Test Case 4: Bookings Management
1. Click "Bookings Management" in sidebar
2. Verify all bookings load in table
3. Test status filter dropdown
4. Test "Confirm Selected" action
5. Test "Cancel Selected" action

### Test Case 5: Payments Management
1. Click "Payments Management" in sidebar
2. Verify payments load in table
3. Test status filter
4. Test method filter
5. Verify revenue totals by method

## Troubleshooting

### Issue: "Cannot resolve symbol 'BookingDAO'"
**Solution:** Make sure BookingDAO.java and PaymentDAO.java files are in src/main/java/com/example/dp/dao/
- Rebuild project: `mvn clean compile`
- Refresh IDE cache: File > Invalidate Caches

### Issue: Admin dashboard not loading
**Solution:** Check LoginController.java modification:
- Verify user.getRole() returns "ADMIN" or "admin"
- Check case sensitivity

### Issue: Tables show no data
**Solution:** Verify database connections:
```java
// In DatabaseConnection.java, verify credentials:
private final String URL = "jdbc:mysql://localhost:3306/cinema_system";
private final String USER = "root";
private final String PASSWORD = "Cima12";
```

### Issue: CSS styles not applied
**Solution:** Verify stylesheets path in AdminDashboardController:
```xml
stylesheets="@../css/style.css"
```

## Performance Considerations

1. **Large Datasets:** For tables with many rows, implement pagination:
   ```java
   private static final int PAGE_SIZE = 20;
   private int currentPage = 0;
   
   public void loadPaymentsData(int page) {
       String query = "SELECT * FROM payments LIMIT ? OFFSET ?";
       // ... use LIMIT and OFFSET
   }
   ```

2. **Database Query Optimization:**
   - Add indexes to frequently queried columns:
   ```sql
   CREATE INDEX idx_booking_status ON bookings(booking_status);
   CREATE INDEX idx_payment_status ON payments(payment_status);
   CREATE INDEX idx_movie_status ON movies(status);
   ```

3. **Caching:** For statistics that don't change frequently:
   ```java
   private long lastUpdateTime = 0;
   private static final long CACHE_INTERVAL = 60000; // 1 minute
   
   private boolean shouldRefresh() {
       return System.currentTimeMillis() - lastUpdateTime > CACHE_INTERVAL;
   }
   ```

## Security Considerations

1. **Role-Based Access Control:**
   ```java
   if (!user.getRole().equals("ADMIN")) {
       showError("Access Denied: Admin role required");
       return;
   }
   ```

2. **SQL Injection Prevention:** All DAOs use PreparedStatements ✓

3. **Sensitive Data:** Don't log passwords or sensitive payment info

## Future Enhancements

1. **Advanced Filtering:**
   - Date range filters
   - Text search in tables
   - Multiple filter combinations

2. **Reporting:**
   - Generate PDF reports
   - Export to CSV
   - Email reports

3. **Audit Logging:**
   - Track admin actions
   - Monitor changes
   - Compliance logging

4. **Real-time Updates:**
   - WebSocket notifications
   - Auto-refresh statistics
   - Live booking alerts

5. **Bulk Operations:**
   - Bulk movie upload
   - Bulk status changes
   - Batch payment processing

## Support & Documentation

- Implementation Summary: See ADMIN_DASHBOARD_IMPLEMENTATION.md
- Database Schema: See database_script.sql
- Existing Controllers: LoginController, DashboardController
- Existing DAOs: MovieDAO, ShowtimeDAO, UserDAO

