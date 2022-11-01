## Spring boot transactions - Приложение для иллюстрации декларативного и программного управления транзакциями в экосистеме Spring

Это pet-проект, который показывает как имитировать перевод денег со счета на счет в виде транзакции с разной реализацией.


### Стек технологий
- Java 11
- Spring Boot
- Spring Data JPA (+EntityManager)
- Spring Web
- Spring Transactional (+TransactionManager, TransactionTemplate)
- Lombok
- MapStruct
- Swagger (OpenAPI 3.0)
- H2 database
- Gradle
- Docker

###> H2 database configuration

Приложение использует базу данных H2 In Memory, а консоль находится по адресу http://localhost:8080/transactions-app/h2-console.
URL-адрес jdbc должен быть следующим: jdbc:h2:mem:transaction-system.
Имя пользователя — «sa», пароль отсутствует.

В ресурсах добавлен `data.sql` файл для заполнения данных.

```SQL:title=data.sql
INSERT INTO account (balance, name) VALUES (200,  'Alexander');
INSERT INTO account (balance, name) VALUES (200,  'Petr');
```

## Сборка приложения 
**Необходимо:**
1. Java 11+
2. Docker

### Через jar-файл
```shell script
# Склонировать проект к себе
git clone https://github.com/devalurum/messenger-system.git
# загружает gradle wrapper
gradlew wrapper
# сборка проекта
gradlew clean build 
# запуск Spring сервиса
java -jar build/libs/spring-boot-transactions.jar 
```
### Через docker
```shell script
# Склонировать проект к себе
git clone https://github.com/devalurum/messenger-system.git
# загружает gradle wrapper
gradlew wrapper
# сборка проекта
gradlew clean build 
# сборка образа  
docker build -t spring-boot-transactions .
# запуск docker-контейнера
docker run -d -p 127.0.0.1:8080:8080 --name spring-boot-transactions spring-boot-transactions
```
## OpenAPI описание
1. Откройте адрес в браузере http://localhost:8080/transactions-app/swagger-ui
2. Попробовать отправить запросы из Swagger-UI.
* Поле id при post запросе игнорируются, т.к генерируются на стороне сервера.

## Todo:
- Написать голую реализацию перевода через транзакцию на JDBC.
- Дописать тесты как будет время для всех слоёв и модулей.