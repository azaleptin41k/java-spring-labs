# Простое REST API на Spring Boot

Это учебный проект, созданный для демонстрации работы простых REST-контроллеров с использованием фреймворка Spring Boot.

## 🚀 Технологический стек

- Java 17+
- Spring Boot
- Maven
- Git / GitHub

## Endpoints (Конечные точки API)

Все эндпоинты доступны после запуска приложения по адресу `http://localhost:8080`.

---

### `GET /`
Проверка работоспособности сервера.

- **URL:** `http://localhost:8080/`
- **Ответ:** `Server is running!`

---

### `GET /api/hello`
Возвращает приветствие.

- **Параметр:** `name` (необязательный) - имя для приветствия.
- **Пример 1 (без параметра):** `http://localhost:8080/api/hello`
    - **Ответ:** `Hello, World!`
- **Пример 2 (с параметром):** `http://localhost:8080/api/hello?name=Alice`
    - **Ответ:** `Hello, Alice!`

---

### `GET /api/time`
Возвращает текущее время сервера.

- **URL:** `http://localhost:8080/api/time`
- **Ответ (пример):** `Current server time is: 2025-10-27 15:00:00`

---

### `GET /api/text-transform`
Выполняет трансформацию переданной строки.

- **Параметры:**
    - `text` (String) - строка для обработки.
    - `operation` (String) - тип операции: `uppercase`, `lowercase`, `reverse`.
- **Пример:** `http://localhost:8080/api/text-transform?text=Example&operation=reverse`
- **Ответ:** `elpmaxE`

---

### `GET /api/sequence/{limit}`
Генерирует JSON-массив чисел от 1 до указанного лимита.

- **Переменная в пути:** `limit` (Integer) - верхняя граница последовательности.
- **Пример:** `http://localhost:8080/api/sequence/5`
- **Ответ:** `[1,2,3,4,5]`

---

##  Как запустить локально

1.  **Требования:**
    - Установленный JDK 17 или выше.
    - Установленный Apache Maven.

2.  **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/ВАШЕ_ИМЯ/ИМЯ_РЕПОЗИТОРИЯ.git
    ```

3.  **Перейдите в директорию проекта:**
    ```bash
    cd ИМЯ_РЕПОЗИТОРИЯ
    ```

4.  **Запустите приложение с помощью Maven:**
    ```bash
    mvn spring-boot:run
    ```

Приложение будет доступно по адресу `http://localhost:8080`.