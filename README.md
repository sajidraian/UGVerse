# 🎓 UGVerse — University Academic & Professional Networking Platform

UGVerse is a Java Swing desktop application designed to bring students, faculty members, and alumni together in one university-focused platform. It aims to support academic communication, community interaction, and resource sharing.

## ✨ Features

- 🔐 User login system
- 👥 Role-based dashboard for different user roles
- 🎓 Student, Faculty, and Alumni platform concept
- 📝 Community posts
- 📢 Faculty announcements
- 👤 Profile-related navigation
- 🗄️ MySQL database integration using JDBC
- 🖥️ Desktop graphical interface built with Java Swing

> Note: Feature availability may depend on the current implementation and local database setup.

## 🛠️ Technologies Used

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- JDBC (Java Database Connectivity)
- MySQL
- Apache Ant / NetBeans project structure
- Git and GitHub

## 📂 Project Structure

```text
UGVerse/
├── src/
│   └── ugverse/
│       ├── controller/
│       │   ├── AnnouncementController.java
│       │   ├── LoginController.java
│       │   ├── PostController.java
│       │   └── ProfileController.java
│       ├── model/
│       │   ├── AnnouncementModel.java
│       │   ├── LoginModel.java
│       │   ├── PostModel.java
│       │   └── ProfileModel.java
│       ├── util/
│       │   ├── DBConnection.java
│       │   └── UITheme.java
│       ├── view/
│       │   ├── AnnouncementView.java
│       │   ├── DashboardView.java
│       │   ├── LoginView.java
│       │   └── PostView.java
│       └── UGVerse.java
├── .gitignore
├── build.xml
└── manifest.mf
```

## ⚙️ Requirements

Before running UGVerse, make sure you have:

- Java JDK installed
- NetBeans IDE or a compatible Java IDE
- MySQL Server
- MySQL Connector/J configured in the project
- A MySQL database named `UGVerse_DB`

## 🚀 Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/sajidraian/UGVerse.git
cd UGVerse
```

### 2. Open the project

Open NetBeans, select **File → Open Project**, and choose the cloned `UGVerse` folder.

### 3. Configure MySQL

Start your MySQL server and create the database:

```sql
CREATE DATABASE UGVerse_DB;
```

Create the required tables using the project's database schema. The application expects database tables for users, profiles, community posts, and announcements.

### 4. Configure database credentials

UGVerse reads the MySQL password from the `UGVERSE_DB_PASSWORD` environment variable.

On Windows PowerShell, run:

```powershell
[Environment]::SetEnvironmentVariable(
    "UGVERSE_DB_PASSWORD",
    "YOUR_MYSQL_PASSWORD",
    "User"
)
```

Replace `YOUR_MYSQL_PASSWORD` with your own local MySQL password. Restart NetBeans afterward so it can read the updated environment variable.

The default database username in the current configuration is `root`.

### 5. Build and run

In NetBeans:

1. Right-click the `UGVerse` project.
2. Select **Clean and Build**.
3. Run the project using its main application class, `ugverse.UGVerse`.

## 🏗️ Architecture

The project organizes its Java code into separate packages:

- **Model:** Handles application data and database-related operations.
- **View:** Contains Swing interfaces and screens.
- **Controller:** Connects user actions with application logic.
- **Util:** Contains shared utilities, including database connectivity and UI styling.

This separation helps keep the application organized and easier to maintain.

## 🔮 Future Improvements

- Complete and test all role-specific workflows
- Add SQL schema and sample database data scripts
- Improve profile management
- Add search and filtering for community posts and announcements
- Improve validation and error handling
- Add application screenshots and a demonstration video
- Expand resource-sharing and academic collaboration features

## 👨‍💻 Author

**Sajid Al Raian**

- GitHub: [@sajidraian](https://github.com/sajidraian)
- Project: [UGVerse](https://github.com/sajidraian/UGVerse)

---

*UGVerse is an academic project focused on Java Swing, OOP, JDBC, and MySQL.*
