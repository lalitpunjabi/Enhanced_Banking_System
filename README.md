# Enhanced_Banking_System

## Overview
Enhanced Banking System is a simple Java-based banking application that allows users to securely manage their account, perform transactions, and apply interest. It includes authentication, PIN management, and transaction tracking features.

## Features
- Secure authentication using hashed PINs (SHA-256)
- Balance inquiry
- Deposits and withdrawals with daily limits
- Transaction history tracking
- Interest application on balance
- PIN change and recovery
- Account lockout after multiple failed login attempts

## Requirements
- Java Development Kit (JDK) 8 or higher

## How to Run
1. Clone this repository:
   ```sh
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```sh
   cd EnhancedBanking
   ```
3. Compile the Java file:
   ```sh
   javac EnhancedBanking.java
   ```
4. Run the program:
   ```sh
   java EnhancedBanking
   ```

## Usage
1. Enter the correct PIN to authenticate.
2. Choose an option from the menu:
   - View balance
   - Deposit funds
   - Withdraw funds (limited to 3 withdrawals per day)
   - View transaction history
   - Apply monthly interest (based on an annual interest rate of 3%)
   - Change or reset PIN
3. Exit the application when done.

## Security Measures
- PINs are securely hashed before storage.
- Users get locked out after 3 failed authentication attempts for 30 seconds.
- PIN change requires authentication with the current PIN.
- PIN reset requires identity verification via a simulated OTP.

## License
This project is open-source under the MIT License.

## Author
Lalit Punjabi

