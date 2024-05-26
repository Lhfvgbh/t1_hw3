# Проект №3 в рамках открытой школы Т1

## Описание
Реализация Spring Boot Starter для логирования HTTP запросов:
t1_hw3-spring-boot-starter - реализация Spring Boot Starter
t1_hw3-starter-user - проект для проверки работы стартера (на основе [проекта №1](https://github.com/Lhfvgbh/t1_hw1))

Для проверки функционала может быть использована коллекция Postman


## Для настройки уровня и вида логирования может быть использован файл [log4j2.xml](src%2Fmain%2Fresources%2Flog4j2.xml)
Исходный уровень логирования - INFO

## Сборка приложения t1_hw3-spring-boot-starter
```shell script
# сборка проекта, прогон unit-тестов, подготовка компонента
mvn clean install

```

## Сборка приложения t1_hw3-starter-user
```shell script
# после сборки t1_hw3-spring-boot-starter локальная версия компонента может быть найдена в директории
.m2/repository/com/example

# в t1_hw3-starter-user указана зависимость в эту директорию:
<dependency>
	<groupId>com.example</groupId>
	<artifactId>t1_hw3-spring-boot-starter</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>

# запустить PostgreSQL в docker-контейнере
docker-compose up -d postgres

# сборка проекта, прогон unit-тестов,
mvn clean install

# запуск приложения из корневой папки проекта
java -jar ./target/t1_hw3-starter-user-0.0.1-SNAPSHOT.jar
```