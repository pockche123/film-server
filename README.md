# film-server
Film App
The Film App is a Java-based application that allows users to browse and manage a collection of films. It provides features for searching films, viewing details, and managing user accounts.

Prerequisites
Before running the Film App, ensure that the following prerequisites are met:

Java Development Kit (JDK) 17 or higher is installed.
Maven build tool is installed.
Getting Started
To get started with the Film App, follow these steps:

Clone the repository:

shell
Copy code
git clone https://github.com/pockche123/film-server.git
Navigate to the project directory:

shell
Copy code
cd film-app
Build the project using Maven:

shell
Copy code
mvn clean package
Run the application:

shell
Copy code
java -jar target/film-app-server.jar
Access the Film App in your web browser:

arduino
Copy code
http://localhost:8080
Features
The Film App provides the following features:

Search: Users can search for films based on title, genre, or other criteria.
Film Details: Users can view detailed information about a film, including its synopsis, cast, and release date.
User Accounts: Users can create accounts, log in, and manage their profile information.
Favorites: Logged-in users can mark films as favorites and easily access them later.
Reviews: Users can write reviews and rate films.
Admin Dashboard: Administrators have access to a dashboard where they can manage films, users, and reviews.
Technologies Used
The Film App is built using the following technologies and frameworks:

Java
Spring Boot
Spring Web
Spring Security
Spring Data MongoDB
WebSocket
Maven
JSON Web Tokens (JWT)
Jackson JSON Library
Lombok
MongoDB
Configuration
The Film App uses environment variables for configuration. Before running the application, make sure to set the following environment variables:

DB_HOST: MongoDB database host address
DB_PORT: MongoDB database port number
DB_NAME: MongoDB database name
JWT_SECRET: Secret key for JWT token generation
Contributing
Contributions to the Film App are welcome! If you encounter any issues or have suggestions for improvements, please submit an issue or a pull request to the GitHub repository.


Contact
For any inquiries or questions, please contact the project maintainer:

Parjal Rai
Email: bayingray123@gmail.com

Thank you for using the Film App! I hope you enjoy managing your film collection with ease.
