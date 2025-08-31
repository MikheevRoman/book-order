# Описание задания

Вам необходимо реализовать простое веб-приложение по заказу книг в библиотеке.
Простой веб интерфейс должен быть сделан на Spring Web. Приложение должно выводить список книг, позволять добавить книгу (наименование, автор, ISBN), редактировать книгу, список клиентов, добавить клиента (ФИО, дата рождения), редактировать клиента, интерфейс взятия книги на прочтение (считаем, что количество книг в библиотеке одного ISBN бесконечно).
Так же необходимо реализовать Rest интерфейс, который будет возвращать JSON со всеми читающими клиентами, что они взяли и когда, для простоты это должен быть список объектов со следующими полями (ФИО клиента, дата рождения клиента, наименование книги, автор книги, ISBN книги, дата взятия книги на прочтение.

# Стек

Spring Boot 3,
Spring Web, Spring Data JPA,
Hibernate,
PostgreSQL,
Thymeleaf,
Lombok,
Maven

# Инструкция по запуску приложения через Docker Compose

## 0. Требования

* [Docker](https://docs.docker.com/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/)

---

## 1. Запуск приложения

В корне проекта (где находится `docker-compose.yml`) выполните:

```bash
docker compose up --build
```

---

## 2. Проверка запуска

* База данных PostgreSQL будет доступна на порту **5431** локально.
* Приложение будет доступно по адресу: [http://localhost:8080](http://localhost:8080)

Теперь приложение готово к использованию:

* Меню: [http://localhost:8080](http://localhost:8080)
* Список книг: [http://localhost:8080/books](http://localhost:8080/books)
* Список клиентов: [http://localhost:8080/clients](http://localhost:8080/clients)
* Список заказов: [http://localhost:8080/orders](http://localhost:8080/orders)
* REST API: [http://localhost:8080/api/reading-clients](http://localhost:8080/api/reading-clients)


---

## Управление контейнерами

Остановить контейнеры:

```bash
docker compose down
```

---

## Схема БД

Схема базы данных автоматически создаётся при старте приложения благодаря настройке:

```
spring.jpa.hibernate.ddl-auto=update
```

---

# Инструкция по запуску приложения руками

## 0. Требования

* Java 17+
* Maven 3.9+
* PostgreSQL (локально или в Docker)

## 1. Настройка БД

1. Создайте базу данных (например, `book_order`):

   ```sql
   CREATE DATABASE book_order;
   ```
2. В файле `src/main/resources/application.properties` укажите настройки подключения:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/book_order
   spring.datasource.username=your_user
   spring.datasource.password=your_password
   ```

## 2. Сборка проекта

```bash
mvn clean package
```

После этого в папке `target/` появится исполняемый JAR, например:

```
target/book-order-1.0.0.jar
```

## 3. Запуск приложения

```bash
java -jar target/book-order-1.0.0.jar
```

После запуска:

* Веб-интерфейс доступен по адресу: [http://localhost:8080](http://localhost:8080)
* REST-эндпоинт для читающих клиентов:

  ```
  GET http://localhost:8080/api/reading-clients
  ```

---