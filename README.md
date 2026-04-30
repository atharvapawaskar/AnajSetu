

# 🌾 AnajSetu — Food Rescue & Management System

### *Bridging the gap between surplus food and those in need*

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=flat-square&logo=react&logoColor=black)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?style=flat-square&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Vite](https://img.shields.io/badge/Vite-5.x-646CFF?style=flat-square&logo=vite&logoColor=white)](https://vitejs.dev/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-3.x-06B6D4?style=flat-square&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen?style=flat-square)](CONTRIBUTING.md)

---

> **AnajSetu** (अनाज सेतु) translates to *"Food Bridge "* in Hindi — a platform that connects food donors (restaurants, hotels, households) with NGOs and shelters to reduce food waste across India.

</div>

---

## 📋 Table of Contents

- [✨ Features](#-features)
- [🖼️ Screenshots](#️-screenshots)
- [🏗️ System Architecture](#️-system-architecture)
- [🚀 Tech Stack](#-tech-stack)
- [⚙️ Setup & Installation](#️-setup--installation)
- [🔐 Environment Variables](#-environment-variables)
- [📡 API Reference](#-api-reference)
- [🗄️ Database Schema](#️-database-schema)
- [👥 Roles & Permissions](#-roles--permissions)
- [📁 Project Structure](#-project-structure)
- [🧪 Testing](#-testing)
- [🤝 Contributing](#-contributing)


---

## ✨ Features

### 🧑‍🤝‍🧑 For Donors
- Register and manage your donor profile
- Post surplus food donations with quantity, type, and pickup time
- Track the status of each donation in real-time
- View donation history and impact stats (meals served, kg saved)

### 🏢 For NGOs
- Browse and claim available food donations near your location
- Manage pickup schedules and volunteer assignments
- Generate collection and distribution reports

### 🛡️ For Admins
- Approve/reject NGO and Donor registrations
- Monitor platform activity via a live dashboard
- Manage categories, cities, and platform-level settings

### 🌐 General Platform Features
- JWT-based authentication with role-based access control (RBAC)
- Real-time donation status updates
- Responsive UI for mobile and desktop
- RESTful API with full Swagger/OpenAPI documentation
- Secure password hashing (BCrypt)
- Email notifications via JavaMail



## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                        CLIENT TIER                       │
│         React + Vite + TypeScript + Tailwind CSS         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │
│  │  Donor UI   │  │   NGO UI    │  │    Admin UI     │  │
│  └──────┬──────┘  └──────┬──────┘  └────────┬────────┘  │
└─────────┼────────────────┼─────────────────-┼───────────┘
          │          HTTP / REST API           │
          │         (JWT Bearer Token)         │
┌─────────▼────────────────▼──────────────────▼───────────┐
│                      BUSINESS TIER                        │
│                  Spring Boot 3.x (Java 17)                │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌─────────┐  │
│  │  Auth    │  │ Donation │  │   NGO    │  │  Admin  │  │
│  │ Service  │  │ Service  │  │ Service  │  │ Service │  │
│  └──────────┘  └──────────┘  └──────────┘  └─────────┘  │
│  ┌──────────────────────────────────────────────────┐     │
│  │      Spring Security + JWT + BCrypt              │     │
│  └──────────────────────────────────────────────────┘     │
└──────────────────────────┬──────────────────────────────-┘
                           │  JPA / Hibernate
┌──────────────────────────▼──────────────────────────────-┐
│                        DATA TIER                          │
│                    MySQL Database                         │
│   Users │ Donations │ NGOs │ Pickups │ Notifications     │
└──────────────────────────────────────────────────────────┘
```

The project follows a classic **3-Tier Client-Server Architecture**:

| Tier | Technology | Responsibility |
|------|------------|----------------|
| **Client Tier** | Next.js + Tailwind CSS + TypeScript | UI rendering, routing, state management |
| **Business Tier** | Spring Boot 3.x + Java 17 | REST API, business logic, authentication |
| **Data Tier** | MySQL 8.x + JPA/Hibernate | Persistent storage, data integrity |

---

## 🚀 Tech Stack

### Frontend

| Technology | Version | Purpose |
|------------|---------|---------|

| [TypeScript](https://www.typescriptlang.org/) | 5.x | Type safety |
| [Tailwind CSS](https://tailwindcss.com/) | 3.x | Utility-first styling |
| [React Router](https://reactrouter.com/) | 6.x | Client-side routing |
| [Axios](https://axios-http.com/) | 1.x | HTTP client |
| [React Query](https://tanstack.com/query) | 5.x | Server state management |

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| [Java](https://www.java.com/) | 17 (LTS) | Core language |
| [Spring Boot](https://spring.io/projects/spring-boot) | 3.x | Application framework |
| [Spring Security](https://spring.io/projects/spring-security) | 6.x | Auth & authorization |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | 3.x | ORM layer |
| [Hibernate](https://hibernate.org/) | 6.x | JPA implementation |
| [Maven](https://maven.apache.org/) | 3.9+ | Build & dependency management |
| [JWT (jjwt)](https://github.com/jwtk/jjwt) | 0.12.x | Token-based auth |
| [JavaMail](https://javaee.github.io/javamail/) | — | Email notifications |
| [Lombok](https://projectlombok.org/) | — | Boilerplate reduction |
| [MapStruct](https://mapstruct.org/) | 1.5.x | DTO mapping |

### Database & Tools

| Tool | Purpose |
|------|---------|
| MySQL 8.x | Primary relational database |
| MySQL Workbench | Database design & querying |
| Postman | API testing & collection management |
| Swagger / OpenAPI | API documentation |

---

## ⚙️ Setup & Installation

### ✅ Prerequisites

Make sure the following are installed on your machine:

| Requirement | Minimum Version | Check Command |
|-------------|-----------------|---------------|
| JDK | 17 | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| Node.js | 18.x | `node -v` |
| npm | 9.x | `npm -v` |
| MySQL Server | 8.x | `mysql --version` |
| Git | Any | `git --version` |

---

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/AnajSetu.git
cd AnajSetu
```

---

### 2. Database Setup

Open **MySQL Workbench** or your MySQL client and run:

```sql
CREATE DATABASE anajsetu_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'anajsetu_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON anajsetu_db.* TO 'anajsetu_user'@'localhost';
FLUSH PRIVILEGES;
```

---

### 3. Backend Setup

```bash
# Navigate to backend folder
cd AnajSetu-Backend

# Copy and configure environment properties
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` with your database credentials (see [Environment Variables](#-environment-variables)):

```bash
# Run the Spring Boot application
./mvnw spring-boot:run

# Or on Windows
.\mvnw spring-boot:run
```

The backend will start at: **`http://localhost:8080`**

Swagger UI will be available at: **`http://localhost:8080/swagger-ui/index.html`**

---

### 4. Frontend Setup

```bash
# Navigate to frontend folder (in a new terminal)
cd AnajSetu-Frontend

# Install dependencies
npm install

# Copy environment file
cp .env.example .env

# Start the development server
npm run dev
```

The frontend will start at: **`http://localhost:5173`**

---

### 5. Quick Start (All-in-one)

If you have `make` installed:

```bash
make setup   # Sets up DB, installs dependencies
make start   # Starts both backend and frontend
make test    # Runs all tests
```

---

## 🔐 Environment Variables

### Backend — `application.properties`

```properties
# ── Server ──────────────────────────────────────────────────
server.port=8080

# ── Database ─────────────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/anajsetu_db
spring.datasource.username=anajsetu_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ── JPA / Hibernate ───────────────────────────────────────────
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# ── JWT ───────────────────────────────────────────────────────
jwt.secret=your_super_secret_key_here_min_256_bits
jwt.expiration=86400000

# ── Mail ─────────────────────────────────────────────────────
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Frontend — `.env`

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
VITE_APP_NAME=AnajSetu
```

> ⚠️ **Never commit** `.env` or `application.properties` with real credentials. Add them to `.gitignore`.

---

## 📡 API Reference

Base URL: `http://localhost:8080/api/v1`

All protected endpoints require the header:
```
Authorization: Bearer <jwt_token>
```

### Auth Endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/auth/register` | Public | Register as Donor or NGO |
| `POST` | `/auth/login` | Public | Login and receive JWT token |
| `POST` | `/auth/logout` | Auth | Invalidate token |
| `GET` | `/auth/me` | Auth | Get current user profile |

### Donation Endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/donations` | Donor | Create a new donation |
| `GET` | `/donations` | NGO, Admin | List all available donations |
| `GET` | `/donations/{id}` | Auth | Get donation by ID |
| `PUT` | `/donations/{id}` | Donor | Update own donation |
| `DELETE` | `/donations/{id}` | Donor, Admin | Cancel a donation |
| `PATCH` | `/donations/{id}/claim` | NGO | Claim a donation |
| `PATCH` | `/donations/{id}/complete` | NGO | Mark donation as collected |

### NGO Endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/ngos` | Admin | List all NGOs |
| `GET` | `/ngos/{id}` | Auth | Get NGO details |
| `PUT` | `/ngos/{id}` | NGO | Update NGO profile |
| `PATCH` | `/ngos/{id}/approve` | Admin | Approve NGO registration |

### Admin Endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/admin/dashboard` | Admin | Platform stats summary |
| `GET` | `/admin/users` | Admin | List all users |
| `DELETE` | `/admin/users/{id}` | Admin | Remove a user |

> 📖 Full interactive API documentation: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## 🗄️ Database Schema

```
┌──────────────┐       ┌──────────────────┐       ┌──────────────┐
│    users     │       │    donations     │       │     ngos     │
│──────────────│       │──────────────────│       │──────────────│
│ id (PK)      │       │ id (PK)          │       │ id (PK)      │
│ name         │       │ donor_id (FK)    │──────▶│ user_id (FK) │
│ email        │──────▶│ ngo_id (FK)      │       │ org_name     │
│ password     │       │ food_type        │       │ address      │
│ phone        │       │ quantity_kg       │       │ city         │
│ role         │       │ pickup_time      │       │ approved     │
│ city         │       │ status           │       │ created_at   │
│ created_at   │       │ description      │       └──────────────┘
└──────────────┘       │ created_at       │
                       └──────────────────┘
                                │
                       ┌────────▼─────────┐
                       │     pickups      │
                       │──────────────────│
                       │ id (PK)          │
                       │ donation_id (FK) │
                       │ ngo_id (FK)      │
                       │ pickup_time      │
                       │ notes            │
                       │ status           │
                       └──────────────────┘
```

> 📁 Full SQL schema file: [`docs/schema.sql`](docs/schema.sql)

---

## 👥 Roles & Permissions

| Feature | Guest | Donor | NGO | Admin |
|---------|:-----:|:-----:|:---:|:-----:|
| View landing page | ✅ | ✅ | ✅ | ✅ |
| Register / Login | ✅ | ✅ | ✅ | ✅ |
| Post a donation | ❌ | ✅ | ❌ | ✅ |
| Edit own donation | ❌ | ✅ | ❌ | ✅ |
| Claim a donation | ❌ | ❌ | ✅ | ✅ |
| View all donations | ❌ | ❌ | ✅ | ✅ |
| View dashboard | ❌ | ✅ | ✅ | ✅ |
| Approve NGOs | ❌ | ❌ | ❌ | ✅ |
| Manage all users | ❌ | ❌ | ❌ | ✅ |

---

## 📁 Project Structure

```
AnajSetu/
│
├── AnajSetu-Backend/                   # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/anajsetu/
│   │   │   │   ├── config/             # Security, CORS, Swagger config
│   │   │   │   ├── controller/         # REST controllers
│   │   │   │   ├── dto/                # Request/Response DTOs
│   │   │   │   ├── entity/             # JPA entities
│   │   │   │   ├── exception/          # Global exception handling
│   │   │   │   ├── repository/         # Spring Data JPA repositories
│   │   │   │   ├── security/           # JWT filter, UserDetailsService
│   │   │   │   └── service/            # Business logic layer
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── application.properties.example
│   │   └── test/                       # Unit & integration tests
│   └── pom.xml
│
├── AnajSetu-Frontend/                  # React + Vite application
│   ├── public/
│   ├── src/
│   │   ├── api/                        # Axios instances & API calls
│   │   ├── assets/                     # Images, icons, fonts
│   │   ├── components/                 # Reusable UI components
│   │   │   ├── common/                 # Button, Input, Modal, etc.
│   │   │   ├── donor/                  # Donor-specific components
│   │   │   └── ngo/                    # NGO-specific components
│   │   ├── context/                    # Auth context, React Query setup
│   │   ├── hooks/                      # Custom React hooks
│   │   ├── pages/                      # Route-level page components
│   │   │   ├── Auth/
│   │   │   ├── Donor/
│   │   │   ├── NGO/
│   │   │   └── Admin/
│   │   ├── routes/                     # Protected route logic
│   │   ├── types/                      # TypeScript type definitions
│   │   ├── utils/                      # Helper functions
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── .env.example
│   ├── index.html
│   ├── tailwind.config.ts
│   ├── tsconfig.json
│   └── vite.config.ts
│
├── docs/                               # Documentation & assets
│   ├── screenshots/
│   └── schema.sql
│
├── .gitignore
├── CONTRIBUTING.md
├── LICENSE
└── README.md
```

---

## 🧪 Testing

### Backend Tests

```bash
cd AnajSetu-Backend

# Run all tests
./mvnw test

# Run with coverage report (target/site/jacoco/index.html)
./mvnw test jacoco:report
```

### Frontend Tests

```bash
cd AnajSetu-Frontend

# Run unit tests (Vitest)
npm run test

# Run with coverage
npm run coverage
```

### API Testing with Postman

1. Import the collection: [`docs/AnajSetu.postman_collection.json`](docs/AnajSetu.postman_collection.json)
2. Import the environment: [`docs/AnajSetu.postman_environment.json`](docs/AnajSetu.postman_environment.json)
3. Set your `base_url` and run the **Auth** folder first to get a JWT token.
4. The collection auto-sets the Bearer token for subsequent requests.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! 🎉

1. **Fork** the repository
2. **Create** your feature branch: `git checkout -b feature/my-awesome-feature`
3. **Commit** your changes: `git commit -m 'feat: add some awesome feature'`
4. **Push** to the branch: `git push origin feature/my-awesome-feature`
5. **Open a Pull Request**

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

### Commit Message Convention

We follow [Conventional Commits](https://www.conventionalcommits.org/):

| Prefix | When to use |
|--------|-------------|
| `feat:` | A new feature |
| `fix:` | A bug fix |
| `docs:` | Documentation changes only |
| `style:` | Formatting, no logic change |
| `refactor:` | Code restructuring |
| `test:` | Adding or updating tests |
| `chore:` | Build process or dependency updates |

---

## 🛣️ Roadmap

- [x] Role-based authentication (Donor / NGO / Admin)
- [x] Donation CRUD with status tracking
- [x] NGO approval workflow
- [ ] 🗺️ Map integration (Google Maps / Leaflet) for nearby donations
- [ ] 📲 Push notifications (Firebase FCM)
- [ ] 📊 Analytics dashboard with charts (donation trends, food saved)
- [ ] 🌍 Multi-language support (Hindi, Marathi, Tamil, Telugu)
- [ ] 📱 React Native mobile app
- [ ] 🐳 Docker Compose setup for one-command deployment

---

## 👨‍💻 Author

Vedant Rathod
Department of Information Technology
Vidyavardhini’s College of
Engineering and Technology
Vasai, India
vedant.2403664104@vcet.edu.in

Atharva Pawaskar
DepartmentofInformation Technology
Vidyavardhini’s College of
Engineering and Technology
Vasai, India
atharva.2403614105@vcet.edu.in

Om Pawar
DepartmentofInformationTechnology
Vidyavardhini’s College of
Engineering and Technology
Vasai, India
om.2403604110@vcet.edu.in





Made with ❤️ in India 🇮🇳 to fight food waste

**If this project helped you, please consider giving it a ⭐**

</div>
