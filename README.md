# Thesis
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![arc42](https://img.shields.io/badge/architecture-arc42-lightgrey.svg?style=for-the-badge)

![App Screenshot](./docs/images/screenshot-landingpage.png)
**Thesis** is a web application designed to connect students with tutors and topics for their bachelor's theses. It uses matching as well as filtering functions and offers tutors a very flexible profile edit option.

## Features

 - **Filtering & Matching:** Filters and ranks tutors and topics based on specific research fields and completed modules.
 - **GitHub Authentication:** Secure authentication using GitHub OAuth2.
 - **Markdown support:** Tutors use Markdown files for their profile and topic descriptions.
 - **Flexible profiles and topics:** Tutors are offered a very high flexibility when creating their profile and topic pages.


## Deployment
The project is dockerized for consistent deployment across different environments.

### Environment

The application requires a GitHub OAuth2 App and a PostgreSQL Database Connection. The credentials must be entered in an `.env` file, which needs to contain the following information:

**applogin.env**
```env
# GitHub OAuth2 Connection
CLIENT_ID=your_client_id
CLIENT_SECRET=your_client_secret
# Database Connection
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
```

### Quick Start
1. Clone the repository using `git clone <repository-url>`.
2. Configure your environment variables in the root directory as explained above.
3. Launch the application with `docker compose up [-d]`.
4. You can access the application under `http://localhost:8080`.

## Technology Stack
### Backend / Frameworks
- Java (JDK 21)
- Spring Boot
   - Spring Security
   - Spring Data JDBC
   - Spring Validation
- Flyway (Database Migration)
- Thymeleaf (Frontend Templating)

### Quality Assurance
- Checkstyle
- Spotbugs
- ArchUnit
- AssertJ & JUnit 5

### Build Management and Automation
- Gradle (Build Management)
- Docker & Docker Compose (Containerization)

## Architecture 
The Project follows the **Onion Architecture** to ensure a clean encapsulation and high testability which is verified using **ArchUnit**.

## Documentation
The Project was documented in German using the arc42-template. You can find the documentation here: [arc42 Documentation](./doku.md)

## Credits
The project was developed as a university assignment at the **Heinrich Heine University Düsseldorf**. The product concept and guidance were provided by **[Dr. Jens Bendisposto](https://github.com/bendisposto)**. The application was developed and documented by: **[Ryota Kariya](https://github.com/Kaisabu-creator)**, **[Ole Marschik](https://github.com/Plumbum104)** and **[Janik Daub](https://github.com/janik805)**.

## License
The project is licensed under the **[MIT License](LICENSE)**.
