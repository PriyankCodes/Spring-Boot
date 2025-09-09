# Bank Management System

A comprehensive Spring Boot application with JWT-based authentication and role-based access control for managing bank operations.

## Features

### Authentication & Authorization
- JWT token-based authentication
- Role-based access control (ADMIN, CUSTOMER)
- Secure password encoding with BCrypt
- Custom authentication entry point and filters

### Core Entities
- **Users**: System users with roles and status management
- **Customers**: Customer profile management
- **Accounts**: Bank account management with auto-generated account numbers
- **Transactions**: Transaction history and management
- **Transfers**: Money transfers between accounts via beneficiaries
- **Beneficiaries**: Beneficiary management for transfers
- **Fixed Deposits**: FD creation, maturity calculations, and closure
- **Support Tickets**: Customer support ticket management

### API Endpoints

#### Authentication (`/api/auth`)
- `POST /register` - User registration
- `POST /login` - User login

#### Admin Endpoints (`/api/admin`) - Requires ADMIN role
- **User Management**: CRUD operations for users
- **Customer Management**: CRUD operations for customers
- **Account Management**: Account creation and management
- **Transaction Management**: View all transactions
- **Transfer Management**: View and manage transfers
- **Fixed Deposit Management**: FD operations
- **Support Ticket Management**: Ticket resolution
- **Dashboard Statistics**: System overview

#### Customer Endpoints (`/api/customer`) - Requires CUSTOMER or ADMIN role
- **Profile Management**: View and update customer profile
- **Account Operations**: Create accounts, deposit, withdraw, check balance
- **Transaction History**: View account transactions
- **Beneficiary Management**: Add/remove beneficiaries
- **Money Transfers**: Initiate transfers to beneficiaries
- **Fixed Deposits**: Create and manage FDs
- **Support Tickets**: Create and track support tickets
- **Dashboard**: Personal banking overview

## Technology Stack

- **Framework**: Spring Boot 3.5.5
- **Security**: Spring Security with JWT
- **Database**: MySQL with JPA/Hibernate
- **Validation**: Bean Validation (JSR-303)
- **Documentation**: Lombok for boilerplate code reduction
- **Build Tool**: Maven

## Database Configuration

The application uses MySQL database. Configure the following in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_management_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

## Security Features

- JWT tokens with configurable expiration
- Password encryption using BCrypt
- Role-based method security
- CORS configuration for cross-origin requests
- Custom exception handling for authentication errors

## API Response Format

All API responses follow a consistent format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {...},
  "error": null
}
```

## Account Features

- Auto-generated unique account numbers (Format: ACC{timestamp}{3-digit-random})
- Multiple account types: SAVINGS, CURRENT, SALARY
- Account status management: ACTIVE, INACTIVE, BLOCKED, CLOSED
- Real-time balance updates with transaction history

## Fixed Deposit Features

- Auto-generated FD numbers
- Simple interest calculation
- Maturity date calculation
- Premature closure support
- Integration with linked accounts

## Error Handling

Comprehensive global exception handling for:
- Resource not found errors
- Validation errors
- Authentication/Authorization errors
- Business logic errors (insufficient balance, etc.)
- Generic server errors

## Getting Started

1. **Prerequisites**
   - Java 21
   - MySQL 8.0+
   - Maven 3.6+

2. **Setup Database**
   - Create MySQL database or let the application create it automatically
   - Update database credentials in `application.properties`

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access Application**
   - Server runs on: `http://localhost:8080`
   - Register a new user via `/api/auth/register`
   - Login via `/api/auth/login` to get JWT token
   - Use the token in Authorization header: `Bearer <token>`

## Sample API Usage

### Register User
```bash
POST /api/auth/register
{
  "email": "admin@bank.com",
  "password": "password123",
  "phone": "9876543210",
  "role": "ADMIN"
}
```

### Login
```bash
POST /api/auth/login
{
  "email": "admin@bank.com",
  "password": "password123"
}
```

### Create Account (Customer)
```bash
POST /api/customer/accounts
Authorization: Bearer <token>
{
  "balance": 5000.00,
  "type": "SAVINGS"
}
```

## Architecture

The application follows a layered architecture:
- **Controller Layer**: REST endpoints and request handling
- **Service Layer**: Business logic implementation
- **Repository Layer**: Data access with custom queries
- **Entity Layer**: JPA entities with relationships
- **Security Layer**: Authentication and authorization
- **Exception Layer**: Global error handling

## Database Schema

The system uses the following main tables:
- `users` - System users
- `customers` - Customer profiles
- `accounts` - Bank accounts
- `transactions` - Transaction records
- `transfers` - Money transfers
- `beneficiaries` - Transfer beneficiaries
- `fixed_deposits` - Fixed deposit records
- `support_tickets` - Customer support tickets

All tables include proper relationships, constraints, and indexes for optimal performance.
