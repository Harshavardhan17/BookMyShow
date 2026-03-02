# MySQL 8.4 Setup on macOS (Apple Silicon) using Homebrew

This guide explains how to install, configure, and connect to MySQL 8.4 locally on macOS (M1/M2/M3/M4) using Homebrew. This setup is recommended for local development with Spring Boot and IntelliJ IDEA.

---

# Prerequisites

* macOS (Apple Silicon recommended)
* Homebrew installed

Check brew:

```bash
brew -v
```

If not installed:

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

---

# Step 1: Install MySQL

Install latest MySQL (currently 8.4 LTS):

```bash
brew update
brew install mysql
```

Verify installation:

```bash
mysql --version
```

Example output:

```
mysql  Ver 8.4.x for macos
```

---

# Step 2: Start MySQL Service

Start MySQL:

```bash
brew services start mysql
```

Check status:

```bash
brew services list
```

Expected:

```
mysql started
```

MySQL runs on:

```
Host: 127.0.0.1
Port: 3306
```

---

# Step 3: Secure MySQL Installation

Run:

```bash
mysql_secure_installation
```

Recommended answers:

```
Enter current password: (press Enter if none)

Set root password? → YES

New password: root
Confirm password: root

Remove anonymous users? → YES
Disallow root login remotely? → NO
Remove test database? → YES
Reload privilege tables? → YES
```

---

# Step 4: Login to MySQL

```bash
mysql -u root -p
```

Enter password:

```
root
```

---

# Step 5: Create Database and User

Inside MySQL shell:

```sql
CREATE DATABASE bookmyshowdb;

CREATE USER 'bookmyshowuser'@'localhost' IDENTIFIED BY 'bookmyshowpass';

GRANT ALL PRIVILEGES ON bookmyshowdb.* TO 'bookmyshowuser'@'localhost';

FLUSH PRIVILEGES;
```

Verify:

```sql
SHOW DATABASES;
```

Exit:

```sql
exit;
```

---

# Step 6: Connect via IntelliJ IDEA

Open:

Database → Add Data Source → MySQL

Enter:

```
Host: 127.0.0.1
Port: 3306
Database: bookmyshowdb

User: bookmyshowuser
Password: bookmyshowpass
```

Driver:

```
MySQL Connector/J 8.4.0
```

Click:

```
Test Connection
```

---

# Step 7: Spring Boot Configuration

application.properties:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/bookmyshowdb
spring.datasource.username=bookmyshowuser
spring.datasource.password=bookmyshowpass

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Step 8: Useful Commands

## Stop MySQL

```bash
brew services stop mysql
```

## Start MySQL

```bash
brew services start mysql
```

## Restart MySQL

```bash
brew services restart mysql
```

---

# Step 9: Verify MySQL is running

```bash
lsof -i :3306
```

Expected:

```
mysqld LISTEN
```

---

# Step 10: MySQL File Locations (Apple Silicon)

| Type   | Location                 |
| ------ | ------------------------ |
| Data   | /opt/homebrew/var/mysql  |
| Config | /opt/homebrew/etc/my.cnf |
| Logs   | /opt/homebrew/var/mysql  |

---

# Troubleshooting

## Access denied

Reset password:

```bash
brew services stop mysql
mysqld_safe --skip-grant-tables &
mysql -u root
```

Then:

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
```

Restart:

```bash
brew services restart mysql
```

---

# Final Setup Summary

| Component     | Value          |
| ------------- | -------------- |
| MySQL Version | 8.4            |
| Host          | localhost      |
| Port          | 3306           |
| Database      | bookmyshowdb   |
| Username      | bookmyshowuser |
| Password      | bookmyshowpass |

---

# Recommended for Development

This setup is:

* Native Apple Silicon support
* Fast and stable
* Ideal for Spring Boot development
* No Docker required

---

# Author

Harshavardhan Katkam

---
