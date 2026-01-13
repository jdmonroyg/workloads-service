# 📊 Workload MS2


Microservice responsible for managing the workload of trainers, including training registration, 
monthly/annual aggregation, and exposure of secure REST endpoints with JWT.

## 🛠 Stack
- **Java 21**
- **Spring Boot 4**
- **Spring Data JPA / Hibernate**
- **Spring Security (JWT)**
- **H2 Database (in-memory)**
- **Maven/Gradle** para build y gestión de dependencias
- **JUnit 5 + Mockito + MockMvc** para pruebas unitarias e integración

---

## 📁 Project Structure

src/main/java/com/epam/workloads/
├── controller         
├── dto                
├── exception          
├── filter             
├── mapper             
├── model              
├── repository         
├── service
