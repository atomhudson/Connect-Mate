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

### Frontend Setup

1. Initialize npm:
   ```bash
   npm init -y
   npm install -D tailwindcss
   npx tailwindcss init
   ```

2. Add paths in `tailwind.config.js`:
   ```js
   content: ["./src/**/*.{html,js}"]
   ```

3. Create `input.css` in `src/main/resources/css`:
   ```css
   @tailwind base;
   @tailwind components;
   @tailwind utilities;
   ```

4. Run tailwind build:
   ```bash
   npx tailwindcss -i .\src\main\resources\static\css\input.css -o .\src\main\resources\static\css\output.css --watch
   ```

5. Add the following CDN to every page:
   ```html
   <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
   <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
   ```

---

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
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/", "/login", "/signup").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll();
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
