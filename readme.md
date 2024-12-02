### Canvas Link:
https://excalidraw.com/#json=SIuQrQnGQr9DGkCVc8BWD,JqVceoohF0UsTfllkzdRmw

### Drive Link for OverView of the project
https://drive.google.com/file/d/1Be_1cAU-Mf7AGwtFQGsg3cJvGMvVv9Rb/view?usp=sharing

# ConnectMate - A Smart Contact Manager using Spring Framework

## Overview

ConnectMate is a smart contact manager application built using the Spring Framework. It offers functionalities like managing contacts, adding social links, role-based access control, and integrating with social media platforms like Google and GitHub for authentication.

This document explains how authentication, authorization, and role-based access control (RBAC) are implemented within the application.

---

## Technologies Used

- **Spring Framework** (Spring Web, Spring Security, Spring Data JPA, Bean Validator)
- **Thymeleaf** for templating
- **OAuth2 Client** for social login (Google, GitHub)
- **MySQL** (or PostgreSQL) for database
- **Cloudinary** for image upload
- **TailwindCSS** for frontend styling
- **Mailtrap** for email authentication

---

## Project Setup

### Dependencies

- **Spring Web**
- **Thymeleaf + HD**
- **Spring Data JPA**
- **Bean Validator**
- **Spring Security**
- **OAuth2 Client**
- **ModelMapper**
- **Spring Starter Mail**
- **Lombok**
- **DevTools**
- **MySQL Connector**
- **Cloudinary API KEY & CLIENT SECRET**
  
---

---


# How To Run The Project?

Follow these steps to set up and run the ConnectMate application:

---

## 1. Download the Project

- Clone the project repository or download the source code as a ZIP file.
- Extract the ZIP file to your desired directory.

---

## 2. Configure the `application.properties` File

### **Database Configuration**

1. **Create a Database**:
   - Open your MySQL client or command-line interface.
   - Run the following command to create a database:
     ```sql
     CREATE DATABASE connectmatescm;
     ```

2. **Update Database Credentials**:
   - Open the `application.properties` file.
   - Update the following fields with your MySQL credentials:
     ```properties
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     ```

---

### **OAuth2 Configuration**

To enable social login (Google and GitHub), create OAuth2 credentials for both platforms.

#### **Google OAuth2 Configuration**
1. **Create a Google OAuth2 Application**:
   - Go to the [Google Cloud Console](https://console.cloud.google.com/).
   - Create a new project.
   - Navigate to **APIs & Services > Credentials**.
   - Click on **Create Credentials** and select **OAuth Client ID**.
   - Set the application type to **Web Application**.
   - Add the following redirect URI:
     ```
     http://localhost:8081/login/oauth2/code/google
     ```
   - Save the credentials and note the **Client ID** and **Client Secret**.

2. **Update Google OAuth2 Configuration**:
   - Replace placeholders in `application.properties`:
     ```properties
     spring.security.oauth2.client.registration.google.client-id=your_google_client_id
     spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret
     ```

#### **GitHub OAuth2 Configuration**
1. **Create a GitHub OAuth2 Application**:
   - Go to the [GitHub Developer Settings](https://github.com/settings/developers).
   - Click on **New OAuth App**.
   - Enter the application details:
     - **Homepage URL**: `http://localhost:8081`
     - **Authorization callback URL**: `http://localhost:8081/login/oauth2/code/github`
   - Save the application and note the **Client ID** and **Client Secret**.

2. **Update GitHub OAuth2 Configuration**:
   - Replace placeholders in `application.properties`:
     ```properties
     spring.security.oauth2.client.registration.github.client-id=your_github_client_id
     spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret
     ```

---

### **Cloudinary Configuration**

For image uploads, configure **Cloudinary**:

1. **Create a Cloudinary Account**:
   - Sign up at [Cloudinary](https://cloudinary.com/).
   - Navigate to **Dashboard** to get your Cloud Name, API Key, and API Secret.

2. **Update Cloudinary Configuration**:
   - Replace placeholders in `application.properties`:
     ```properties
     cloudinary.cloud.name=your_cloud_name
     cloudinary.api.key=your_api_key
     cloudinary.api.secret=your_api_secret
     ```

---

### **Email Configuration**

To enable email notifications, configure an SMTP email service (e.g., Mailtrap).

1. **Create a Mailtrap Account**:
   - Sign up at [Mailtrap](https://mailtrap.io/).
   - Navigate to **Inbox > SMTP Settings** to get your credentials.

2. **Update Email Configuration**:
   - Replace placeholders in `application.properties`:
     ```properties
     spring.mail.host=smtp.mailtrap.io
     spring.mail.port=2525
     spring.mail.username=your_mailtrap_username
     spring.mail.password=your_mailtrap_password
     spring.mail.properties.domain_name=connectMate@demomailtrap.com
     ```

---

## 3. Build and Run the Project

1. Open the project in your preferred IDE (e.g., IntelliJ, Eclipse, STS).
2. Ensure you have Java 11 or higher installed on your system.
3. Run the project using the IDE or through the terminal:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 4. Access the Application

- Open a browser and navigate to:
  ```
  http://localhost:8081
  ```
---

#### Additional Frontend Configuration.

---

#### Frontend Setup (Tailwind CSS)
1. **Initialize NPM**:
   ```bash
   npm init -y
   npm install -D tailwindcss
   npx tailwindcss init
   ```

2. **Configure Tailwind**:
   - Update `tailwind.config.js`:
     ```js
     content: ["./src/**/*.{html,js}"]
     ```

3. **Set Up Tailwind CSS**:
   - Create an `input.css` file in `src/main/resources/css`:
     ```css
     @tailwind base;
     @tailwind components;
     @tailwind utilities;
     ```

4. **Build Tailwind CSS**:
   ```bash
   npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch
   ```
   > **Note**: Keep this command running in the background to monitor CSS changes during development.

## Authentication and Authorization

### 1. Spring Security Configuration

To secure routes and manage user access, **Spring Security** is configured. It ensures that sensitive endpoints are protected based on user roles.

#### Authentication
Authentication in ConnectMate is handled using **Spring Security** with support for form-based login and OAuth2 login via **Google** and **GitHub**.

- **Login with Google**: The application is integrated with Google OAuth2 for social login. OAuth2 dependencies are included in the project, and the **Client ID** and **Client Secret** are set up to connect the app with Google's authentication system.

  ```yaml
  spring:
  security:
  oauth2:
  client:
  registration:
  google:
  client-id: YOUR_GOOGLE_CLIENT_ID
  client-secret: YOUR_GOOGLE_CLIENT_SECRET
  scope: profile, email
  redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
  ```

#### Authorization
Authorization is achieved through **role-based access control** (RBAC), where access to certain pages is restricted based on the user's role (e.g., Admin, User).

Roles are assigned during the signup process or via OAuth2 login. For example:

```java
public enum Role {
    ADMIN, USER;
}
```

- Admins can access all resources.
- Regular users can access only their personal contacts.

---

## Role-Based Access Control (RBAC)

### Role Definitions

Roles are defined as follows:

- **ADMIN**: Has full access to all features (CRUD operations for contacts, user management).
- **USER**: Has limited access, such as viewing and managing only their contacts.

### Securing URLs

URL patterns are configured to restrict access to certain pages based on roles. For instance:

```java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorization rules
            authorize.requestMatchers("admin/**").hasRole("ADMIN");
            authorize.requestMatchers("user/**").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.failureHandler(authenticationFailureHandler);
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        return httpSecurity.build();
    }
```

In this configuration:
- `/admin/**` is accessible only by users with the **ADMIN** role.
- `/user/**` is accessible only by users with the **USER** role.
- `/signup` and `/login` are publicly accessible.

### OAuth2 Integration

OAuth2 authentication is set up for social logins (Google, GitHub). Upon successful login, users are granted the **USER** role by default. Admin users can be manually assigned roles via the application backend.

---

## Features and Implementation

### 1. Signup Page and Form Validation

A signup page is created where users can register by providing their details. The form is validated using **Spring Validation**.

### 2. Contact Management

Users can manage their contacts, with CRUD operations for adding, editing, and deleting contacts. Contacts are stored in the MySQL database.

### 3. Image Upload

Images associated with contacts are uploaded to **Cloudinary**, and the image URL is stored in the database.

### 4. Pagination and Search

Pagination is implemented to display contacts efficiently. Additionally, a search functionality allows users to search contacts based on fields like name, phone number, or email.

### 5. Email Sending (Mailtrap)

For email notifications (e.g., account registration confirmation), **Mailtrap** is used as the email service.

---

## Conclusion

ConnectMate leverages **Spring Security** for authentication and authorization, ensuring secure access control with **role-based access**. OAuth2 integration allows users to sign in using their Google or GitHub accounts, while the application also handles contact management, image uploads, and email notifications.
