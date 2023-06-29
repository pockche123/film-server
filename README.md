# film-server
## Film App

The Film App is a Java-based application that allows users to browse and manage a collection of Studio Ghibli films. It provides features for searching films, viewing details, and managing user accounts.


### Prerequisites 

Before running the Film App, ensure that the following prerequisites are met:

- Java Development Kit (JDK) 17 or higher is installed. <br>
- Maven build tool is installed.

### Getting Started
To get started with the Film App, follow these steps:

 **1. Clone the repository:**

git clone https://github.com/pockche123/film-server.git <br>

**2. Navigate to the project directory:**

cd film-server

**3. Build the project using Maven:**

mvn clean package

**4. Run the application:** 

java -jar target/film-app-server.jar

**5. Access the Film App in your web browser:**

http://localhost:8080

### Features
The Film App provides the following features:

- **Search:** Users can search for films based on title, genre, or other criteria. <br>
- **Film Details:** Users can view detailed information about a film, including its synopsis, availability and release date. <br>
- **User Accounts:** Users can create accounts, log in, and manage their profile information. <br>
- **Favorites:** Logged-in users can mark films as favorites and easily access them later. <br>
- **Reviews:** Users can write reviews and rate films. <br>
- **Admin Dashboard:** Administrators have access to a dashboard where they can manage films, users, and reviews.
  
### Technologies Used

The Film App is built using the following technologies and frameworks:

- Java <br>
- Spring Boot <br>
- Spring Web <br>
- Spring Security <br>
- Spring Data MongoDB <br>
- WebSocket <br>
- Maven <br>
- JSON Web Tokens (JWT) <br>
- Jackson JSON Library <br>
- Lombok <br>
- MongoDB <br>
  
### Configuration

The Film App uses environment variables for configuration. Before running the application, make sure to set the following environment variables:

MONGO_DATABASE="movie-api-db" <br>
MONGO_USER="bayingray123"  <br>
MONGO_PASSWORD="infinite8" <br>
MONGO_CLUSTER="cluster0.cnmqfvq.mongodb.net" <br>
JWT_SECRET: 3F4428472B4B6250655368566D5971337336763979244226452948404D635166 <br>

### Contributing 

Contributions to the Film App are welcome! If you encounter any issues or have suggestions for improvements, please submit an issue or a pull request to the GitHub repository.

### Contact

For any inquiries or questions, please contact the project maintainer:

Parjal Rai <br>
Email: bayingray123@gmail.com 

Thank you for using the Film App! I hope you enjoy managing your film collection with ease.
