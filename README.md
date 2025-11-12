# 🚀 Microservices Architecture – Event Management Platform

A fully modular **Spring Boot microservices ecosystem** designed to manage events efficiently, featuring service discovery, gateway routing, and independent deployment.

---

## 🧭 Overview

This project demonstrates a **distributed microservices architecture** using **Spring Cloud Netflix Eureka** for service discovery and **Spring Cloud Gateway** as the API gateway.  
Each service is independently deployable, while being fully integrated into a centralized system.

---

## 🏗️ Project Structure

microservices-architecture/
│
├── EurekaServer/ # Service discovery (Eureka)
├── Gateway/ # API Gateway (Spring Cloud Gateway)
├── Event/ # Microservice handling event data
├── EventManagement/ # Core service managing event workflows
└── README.md


---

## ⚙️ Core Technologies

| Layer | Technology |
|-------|-------------|
| **Framework** | Spring Boot 3.x |
| **Service Discovery** | Spring Cloud Netflix Eureka |
| **Gateway Routing** | Spring Cloud Gateway |
| **Inter-Service Communication** | OpenFeign / REST |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Security** | Spring Security + JWT (optional) |
| **Containerization** | Docker (optional) |

---

## 🔧 Configuration & Ports

| Service | Default Port | Description |
|----------|---------------|-------------|
| **Eureka Server** | `8761` | Service Registry |
| **Gateway** | `8080` | Main API Gateway |
| **Event Service** | `8082` | Event data microservice |
| **Event Management** | `8083` | Event orchestration service |

---

## 🧩 How to Run

### 1️⃣ Start the Eureka Server
```bash
cd EurekaServer
mvn spring-boot:run


Access: http://localhost:8761

2️⃣ Start the API Gateway
cd Gateway
mvn spring-boot:run


Acts as a single entry point for all client requests.

3️⃣ Start the Microservices
cd Event
mvn spring-boot:run

cd EventManagement
mvn spring-boot:run


Each service automatically registers with Eureka at startup.

🌐 System Architecture
                +-----------------------+
                |  Client / Frontend UI |
                +-----------+-----------+
                            |
                            v
                    +---------------+
                    |   API Gateway  |
                    |   (port 8080)  |
                    +-------+-------+
                            |
            +---------------+---------------+
            |                               |
            v                               v
   +------------------+           +----------------------+
   |   Event Service  |           | EventManagement Svc  |
   |   (port 8082)    |           | (port 8083)          |
   +--------+----------+           +----------+-----------+
            \                           /
             \                         /
              v                       v
              +-----------------------+
              |     Eureka Server     |
              |     (port 8761)       |
              +-----------------------+

🧠 Example application.yml (for one service)
server:
  port: 8082

spring:
  application:
    name: event-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

🐳 Run with Docker (optional)

You can easily containerize each service using Docker:

docker build -t event-service .
docker run -p 8082:8082 event-service


Or use a single docker-compose.yml file to start the entire stack:

docker-compose up --build

📁 .gitignore Recommendation

Make sure to exclude IDE and build files:

/target
/.idea
/.vscode
*.iml
*.log
Future Enhancements

🔐 Integrate authentication with Keycloak or Spring Security JWT

🧩 Add a Notification microservice (email/SMS)

📊 Centralized logging with ELK Stack

📈 Monitoring with Prometheus + Grafana

☁️ Deploy to Docker Swarm or Kubernetes
