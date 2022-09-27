# Fox Dining Project

For this project you will build a (simple) restaurant management and billing system.

See [PROJECT](PROJECT.md)

- [Fox Dining Project](#fox-dining-project)
  - [Dependencies](#dependencies)
  - [Development rules:](#development-rules)
  - [Processes:](#processes)
  - [Useful links](#useful-links)
  - [Git Workflow](#git-workflow)
  - [Material Review](#material-review)

## Dependencies

- Java Development Kit - JDK 8
- Spring Boot Data JPA
- Spring Security
- Spring Web
- Flyway
- Javax Mail
- Java Dotenv
- Java 2FA

## Development rules:

- Do not use `Lombok`
- Do not define getters/setters which are not explicitly used
- Make *everything* configurable (e.g. via values in `.env` and `application.properties`), i.e. no constant value should be hard-coded. The database credentials are already pre-configured this way. See [MATERIALS#Dotenv](#dotenv)
  - For example, all error messages should be stored in `/java/main/resources/messages.properties`
  - i18n-ed messages will then be in:
    - `/java/main/resources/messages_sk.properties`
    - `/java/main/resources/messages_cz.properties`
    - `/java/main/resources/messages_hu.properties`
- Place contents into classes according to this order:
  - Final fields
  - Fields
  - Default constructor
  - Constructors with parameters
  - Getters & Setters
  - Public methods
  - Private methods
- Use Data Transfer Objects (Dto's) for request/response data (e.g. create a `dtos` package (do not put this package into the `models` package) and `ResponseDto` and `RequestDto` abstract classes. 
- Naming
  - Entities/Models
    - Use camelCase for Java properties and snake_case for the corresponding database column names. For example `private String activationToken` in the User model should be mapped to the `activation_token` column in the `users` table
  - Database:
    - Use plurals for database tables, e.g. `users`, `posts`, `likes` (and singular for the corresponding models names, i.e. `User`, `Post`, `Like`)
    - Use `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-incremented fields
  - Tests: use descriptive (test) method names in snake_case (to improve readability):
    - : `can_create_model()`
    - : `is_empty_is_false_for_receipt_with_items()`
    - : `can_add_permissions_to_users()`
    - Have a look at this article [You are naming your tests wrong](https://enterprisecraftsmanship.com/posts/you-naming-tests-wrong/) for details on how to name your tests
  - Endpoints: use all lowercase letters and '-' for spaces
    - : `/user/vouchers`
    - : `/forgot-password`
  - Create descriptive branch names, e.g. feature-user-registration
- All error handling should be done via exceptions (and `@ControllerAdvice`)
- Use the object wrapper for primitive types, e.g. `Long` instead of `long`
- Use `this` keyword only to avoid variable name conflicts
- Use the [code formatting](https://blog.jetbrains.com/idea/2020/06/code-formatting/) feature in Intellij (CTRL+ALT+L / ⌥⌘L)
- Have at least 90% test coverage regarding services (unit test) and controllers (integration tests)
- Use [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
  - Have a look [here](https://stackoverflow.com/questions/42979700/how-to-configure-google-java-code-formatter-in-intellij-idea-17) on how to configure the google java style guides in IntelliJ
  - Make sure to use braces {} with `if`, `else`, `for`, `do` and `while` statements, even when the body is empty or contains only a single statement.
