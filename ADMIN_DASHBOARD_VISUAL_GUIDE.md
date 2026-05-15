# Admin Dashboard - Visual Structure & Features

## Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  StarFlix Admin                                    Admin           │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  📊 Overview      │  Overview Dashboard                          │
│  🎬 Movie Mgmt    │  ┌─────────┬─────────┬─────────┬─────────┐  │
│  🎫 Bookings      │  │ Total   │ Total   │ Active  │ Pending │  │
│  💳 Payments      │  │ Bookings│ Revenue │ Movies  │Bookings │  │
│                   │  │         │         │         │         │  │
│                   │  └─────────┴─────────┴─────────┴─────────┘  │
│                   │                                               │
│                   │  Recent Bookings (Last 10)                   │
│                   │  ┌──┬────────┬─────────┬────────┬──────────┐│ │
│                   │  │ID│  User  │  Movie  │ Amount │  Status  ││ │
│                   │  ├──┼────────┼─────────┼────────┼──────────┤│ │
│                   │  │1 │ John   │ Avatar  │  $30   │ CONFIRMED││ │
│                   │  │2 │ Jane   │ Dune 2  │  $45   │ PENDING  ││ │
│                   │  └──┴────────┴─────────┴────────┴──────────┘│ │
│                   │                                               │
└──────────────────────────────────────────────────────────────────┘
```

## Section 1: Overview Dashboard

### Purpose
Display key metrics and recent activity at a glance.

### Components

#### Statistics Cards (4 total)
```
┌─────────────────┐
│ Total Bookings  │     ┌─────────────────┐
│       42        │     │ Total Revenue   │
└─────────────────┘     │      $1,850     │
                        └─────────────────┘

┌─────────────────┐     ┌─────────────────┐
│ Active Movies   │     │ Pending         │
│       8         │     │ Bookings:    5  │
└─────────────────┘     └─────────────────┘
```

#### Recent Bookings Table
- Columns: Booking ID, User, Movie, Amount, Status, Date
- Shows latest 10 bookings
- Sortable by clicking columns
- Colored status indicators

### API Calls
```java
bookingDAO.getTotalBookingsCount()        // Returns: int
paymentDAO.getTotalRevenuePaid()          // Returns: double
movieDAO.getActiveMoviesCount()           // Returns: int
bookingDAO.getPendingBookingsCount()      // Returns: int
bookingDAO.getRecentBookings(10)          // Returns: List<Booking>
```

---

## Section 2: Movie Management

### Purpose
Manage all movies in the system (CRUD operations).

### Components

#### Action Buttons
```
[+ Add Movie]  [✎ Edit Selected]  [✗ Delete Selected]
```

#### Movies Table
```
┌─────┬──────────┬────────┬──────────┬────────────┐
│Title│Genre     │Duration│Status    │Release Date│
├─────┼──────────┼────────┼──────────┼────────────┤
│Avatar 2│ Sci-Fi  │ 192 min│ NOW_SHOWING│ 2022-12-16│
│Dune 2│ Sci-Fi/Adventure│ 166│ NOW_SHOWING│ 2023-11-01│
│Oppenheimer│Drama│ 180│ COMING_SOON│ 2023-07-21│
│...   │ ...      │ ...    │ ...      │ ...        │
└─────┴──────────┴────────┴──────────┴────────────┘
```

#### Add Movie Dialog
```
Movie Title *:        [________________]
Description *:        [________________]
                      [________________]

Genre *:     [Sci-Fi___▼]  Duration *:  [120_______]
Rating *:    [PG-13____▼]  Language *:  [English___▼]
Release Date*: [2024-01-01]  Status: [NOW_SHOWING▼]
Poster URL *: [________________]

                     [Cancel]  [Save Movie]
```

### Features
- **Add Movie:** Create new movie with all fields
- **Edit Movie:** Modify existing movie details
- **Delete Movie:** Remove movie from system
- **Status Toggle:** Change between NOW_SHOWING and COMING_SOON
- **Form Validation:** Required fields marked with *

### Database Operations
```sql
SELECT * FROM movies
SELECT * FROM movies WHERE id = ?
SELECT COUNT(*) FROM movies WHERE status = 'NOW_SHOWING'
INSERT INTO movies (...)
UPDATE movies SET ... WHERE id = ?
DELETE FROM movies WHERE id = ?
```

---

## Section 3: Bookings Management

### Purpose
Monitor and manage customer bookings.

### Components

#### Filter Controls
```
Filter by Status: [All Statuses▼]  [Confirm Selected]  [Cancel Selected]
```

#### Bookings Table
```
┌────┬────────┬─────────┬──────────┬────────┬────────────┬──────────┐
│ ID │ User   │ Movie   │ Showtime │ Amount │ Status     │ Date     │
├────┼────────┼─────────┼──────────┼────────┼────────────┼──────────┤
│ 1  │ John   │ Avatar  │ 10:00 AM │ $30    │ CONFIRMED  │ 2024-01-10│
│ 2  │ Jane   │ Dune 2  │ 02:00 PM │ $45    │ PENDING    │ 2024-01-11│
│ 3  │ Bob    │ Avatar  │ 07:30 PM │ $30    │ CANCELLED  │ 2024-01-12│
│ ...│ ...    │ ...     │ ...      │ ...    │ ...        │ ...      │
└────┴────────┴─────────┴──────────┴────────┴────────────┴──────────┘
```

### Status Values
- **PENDING:** Awaiting confirmation
- **CONFIRMED:** Booking confirmed, ready for payment
- **CANCELLED:** Booking cancelled

### Features
- **Filter by Status:** PENDING, CONFIRMED, CANCELLED, or All
- **Confirm Booking:** Move from PENDING to CONFIRMED
- **Cancel Booking:** Mark booking as CANCELLED
- **View Details:** Click row to see booking details (future enhancement)
- **Bulk Actions:** Select multiple bookings for batch operations

### Database Operations
```sql
SELECT * FROM bookings
SELECT * FROM bookings WHERE booking_status = ?
SELECT COUNT(*) FROM bookings WHERE booking_status = 'PENDING'
UPDATE bookings SET booking_status = ? WHERE id = ?
SELECT * FROM bookings ORDER BY booking_date DESC LIMIT 10
```

---

## Section 4: Payments Management

### Purpose
Track and manage all payment transactions.

### Components

#### Filter Controls
```
Filter by Status: [All Statuses▼]  Filter by Method: [All Methods▼]
```

#### Revenue Summary Cards
```
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│ Card Payments   │  │ Cash Payments   │  │ Wallet Payments │
│     $1,200      │  │     $450        │  │     $200        │
└─────────────────┘  └─────────────────┘  └─────────────────┘
```

#### Payments Table
```
┌────┬─────────┬────────┬────────┬────────┬────────────┬──────────┐
│ ID │ Booking │ Amount │ Method │ Status │ Date       │ Actions  │
├────┼─────────┼────────┼────────┼────────┼────────────┼──────────┤
│ 1  │ 100     │ $30    │ CARD   │ PAID   │ 2024-01-10 │ ✓ ✗      │
│ 2  │ 101     │ $45    │ CASH   │ PENDING│ 2024-01-11 │ ✓ ✗      │
│ 3  │ 102     │ $30    │ WALLET │ FAILED │ 2024-01-12 │ ✓ ✗      │
│ ...│ ...     │ ...    │ ...    │ ...    │ ...        │ ...      │
└────┴─────────┴────────┴────────┴────────┴────────────┴──────────┘
```

### Payment Methods
- **CARD:** Credit/Debit card payments
- **CASH:** Cash payments
- **WALLET:** Digital wallet payments

### Payment Status
- **PAID:** Payment completed successfully
- **PENDING:** Awaiting payment processing
- **FAILED:** Payment failed

### Features
- **Filter by Status:** PAID, FAILED, PENDING
- **Filter by Method:** CARD, CASH, WALLET
- **Revenue Summary:** Total revenue by payment method
- **Mark as PAID:** Update failed payments
- **Mark as FAILED:** Update pending/paid payments
- **View Details:** Access payment receipt (future enhancement)

### Database Operations
```sql
SELECT * FROM payments
SELECT * FROM payments WHERE payment_status = ? AND payment_method = ?
SELECT COUNT(*) FROM payments
SELECT SUM(amount) FROM payments WHERE payment_status = 'PAID'
SELECT SUM(amount) FROM payments WHERE payment_status = 'PAID' AND payment_method = ?
UPDATE payments SET payment_status = ? WHERE id = ?
```

---

## Color & Styling Reference

### Colors Used
```
Dark Background:     #14071f
Sidebar:             #1b1028
Input Fields:        #241833
Primary Color:       #9126ff
Secondary Color:     #b14cff
Text Primary:        #ffffff (white)
Text Secondary:      #b8b3c7
Text Muted:          #8f87a1
Border:              rgba(255,255,255,0.08)
Success:             #00aa00 (implied)
Error:               #e60026 (red)
```

### Font Sizes
- Header: 20px, bold
- Section Title: 18px, bold
- Card Title: 14px, bold
- Card Value: 24px, bold
- Labels: 14px, bold
- Table Text: 13px
- Button Text: 14px, bold

### Button Styles
```
Primary Button:        Gradient #9126ff → #c43cff
Secondary Button:      Border with #b14cff
Button Hover:          Lighter gradient #a93cff → #db5cff
Button Disabled:       Grayed out
```

---

## Navigation Flow

```
Admin Login (admin@example.com)
        ↓
   ┌────┴────┐
   ↓        ↓
 ADMIN    CUSTOMER
DASHBOARD  DASHBOARD
   ↓
   ├─→ Overview      (Default view)
   │     ├─ Statistics
   │     └─ Recent bookings
   │
   ├─→ Movies        
   │     ├─ Browse all movies
   │     ├─ Add movie → Dialog
   │     ├─ Edit movie → Dialog
   │     └─ Delete movie
   │
   ├─→ Bookings
   │     ├─ Browse bookings
   │     ├─ Filter by status
   │     ├─ Confirm booking
   │     └─ Cancel booking
   │
   └─→ Payments
         ├─ Browse payments
         ├─ Filter by status/method
         ├─ Revenue summary
         └─ Update payment status
```

---

## Data Model Relationships

```
┌──────────┐
│  USERS   │
│ (id, PK) │
└────┬─────┘
     │
     ├─→ BOOKINGS (user_id FK)
     │   └─→ PAYMENTS (booking_id FK)
     │   └─→ SHOWTIMES (showtime_id FK)
     │
     └─→ ADMINS (if separate table needed)

┌──────────┐
│ MOVIES   │
│ (id, PK) │
└────┬─────┘
     │
     └─→ SHOWTIMES (movie_id FK)
         └─→ BOOKINGS (showtime_id FK)
             └─→ PAYMENTS

┌──────────────┐
│CINEMA_HALLS  │
│ (id, PK)     │
└────┬─────────┘
     │
     └─→ SEATS (hall_id FK)
         └─→ BOOKING_SEATS
```

---

## Response Times & Performance

### Typical Query Times
- Load overview (4 stats): ~200ms
- Load movies table: ~150ms (for 50 movies)
- Load bookings table: ~200ms (for 100 bookings)
- Load payments table: ~250ms (for 200 payments)
- Update payment status: ~50ms

### Optimization Tips
1. Add database indexes on frequently filtered columns
2. Implement pagination for large tables
3. Cache statistics that don't change frequently
4. Use thread pools for batch operations

---

## Keyboard Shortcuts (Future Enhancement)

```
Ctrl+A    - Add new record
Ctrl+E    - Edit selected
Ctrl+D    - Delete selected
Ctrl+S    - Save dialog
Ctrl+Q    - Close dialog
Alt+1     - Switch to Overview
Alt+2     - Switch to Movies
Alt+3     - Switch to Bookings
Alt+4     - Switch to Payments
```

