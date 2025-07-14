
# 💸 FinanceLedger

A secure, full-stack Personal Finance Management web app built with **Spring Boot** and **ReactJS**. Users can register, log in, manage their account balance, view transaction history, confirm transfers via **OTP sent via SMS**, and download statements in CSV format.

---

## 🛠 Tech Stack

### 🔧 Backend
- Java 17
- Spring Boot (REST APIs)
- Spring Security with JWT Auth
- Hibernate + JPA
- PostgreSQL
- Twilio (for sending OTP via SMS)

### 🌐 Frontend
- ReactJS
- Tailwind CSS (for styling)
- Fetch API for REST calls

---

## ✨ Features

- ✅ User Registration and Login with JWT
- 💳 Deposit Money to Account
- 🔁 Secure Transfer to Another Account (OTP based)
- 📩 OTP Delivery via SMS using Twilio
- 📃 Download Transaction History as CSV
- 📜 Transaction Log View in UI
- 📱 SMS Notification for Each Transaction



---

##  Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/your-username/FinanceLedger.git
cd FinanceLedger
```

---

### 2. Backend Setup

####  Location: `/backend`

- Configure **PostgreSQL DB** and **Twilio** credentials in `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:3306/financeledger
spring.datasource.username=root
spring.datasource.password=yourpassword

twilio.accountSid=ACxxxxxxxxxxxxxxxxxxxxxx
twilio.authToken=xxxxxxxxxxxxxxxxxxxxxx
twilio.fromNumber=+1xxxxxxxxxx
```

- Run backend:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Server runs on: `http://localhost:8080`

---

### 3. Frontend Setup

####  Location: `/frontend`

- Create `.env` file:

```env
REACT_APP_API=http://localhost:8080
```

- Run React app:

```bash
cd frontend
npm install
npm start
```

App runs on: `http://localhost:3000`

---

##  API Testing

You can use the React frontend or Postman to test:

- `POST /auth/register`  
- `POST /auth/login`
- `POST /accounts/deposit?amount=`
- `POST /accounts/transfer/request-otp`
- `POST /accounts/transfer/confirm?to=&amount=&locationChange=&otp=`
- `GET /accounts/me`
- `GET /accounts/me/transactions`
- `GET /accounts/me/transactions/download`

---

##  Folder Structure

```
FinanceLedger/
├── backend/          # Spring Boot app
│   └── src/
│   └── pom.xml
├── frontend/         # React app
│   └── src/
│   └── public/
├── README.md
└── .gitignore
```

---

##  Author

**Divyanshu Srivastava**  
B.Tech ECE @ MANIT  



