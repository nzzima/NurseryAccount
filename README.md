# Итоговая контрольная работа 
### Информация о проекте
Необходимо организовать систему учета для питомника в котором живут
домашние и вьючные животные.  

### Задание  
1. Используя команду cat в терминале операционной системы Linux, создать
   два файла: Домашние животные (заполнив файл собаками, кошками,
   хомяками) и Вьючные животными (заполнив файл Лошадьми, верблюдами и
   ослы), а затем объединить их. Просмотреть содержимое созданного файла.
   Переименовать файл, дав ему новое имя (Друзья человека).

![Screenshot](/source/s1.png)  

2. Создать директорию, переместить файл туда.  

![Screenshot](/source/s2.png)  
![Screenshot](/source/s3.png)  
![Screenshot](/source/s4.png)  

3. Подключить дополнительный репозиторий MySQL. Установить любой пакет
   из этого репозитория.  

![Screenshot](/source/s5.png)  
![Screenshot](/source/s6.png)  

4. Установить и удалить deb-пакет с помощью dpkg.  

![Screenshot](/source/s11.png)
![Screenshot](/source/s12.png)

5. Выложить историю команд в терминале ubuntu. 

```bash
cat >> Pets
cat >> PackAnimals
cat Pets PackAnimals > HumanFriends
cat HumanFriends
```
```bash
mkdir GBdir
cd GBdir/
mv HumanFriends ~/GBdir
ls -l
```
```bash
sudo wget https://dev.mysql.com/get/mysql-apt-config_0.8.22-1_all.deb
sudo dpkg -i mysql-apt-config_0.8.22-1_all.deb
sudo apt-get update
sudo apt-get install mysql-server
```
```bash
sudo wget https://download.docker.com/linux/ubuntu/dists/jammy/pool/stable/amd64/docker-ce-cli_20.10.13~3-0~ubuntu-jammy_amd64.deb
sudo dpkg -i docker-ce-cli_20.10.133-0ubuntu-jammy_amd64.deb
sudo dpkg -r docker-ce-cli
```

6. Нарисовать диаграмму, в которой есть родительский класс, домашние
   животные и вьючные животные, в составы которых в случае домашних
   животных войдут классы: собаки, кошки, хомяки, а в класс вьючные животные
   войдут: Лошади, верблюды и ослы.

![Screenshot](/source/s8.png)

7. В подключенном MySQL репозитории создать базу данных “Друзья
   человека”

```sql
CREATE DATABASE Human_friends;
```

8. Создать таблицы с иерархией из диаграммы в БД

```sql
CREATE TABLE animal_classes
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Class_name VARCHAR(20)
);

INSERT INTO animal_classes (Class_name)
VALUES ('Вьючные'),
       ('Домашние');

CREATE TABLE home_animals
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Genus_name VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO home_animals (Genus_name, Class_id)
VALUES ('Кошки', 2),
       ('Собаки', 2),
       ('Хомяки', 2);

CREATE TABLE cats
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE dogs
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE hamsters
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE packed_animals
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Genus_name VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO packed_animals (Genus_name, Class_id)
VALUES ('Лошади', 1),
       ('Ослы', 1),
       ('Верблюды', 1);

CREATE TABLE horses
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE donkeys
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE camels
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
```
Результат:  

![Screenshot](/source/s13.png)

9. Заполнить низкоуровневые таблицы именами(животных), командами
   которые они выполняют и датами рождения

```sql
INSERT INTO cats (Name, Birthday, Commands, Genus_id)
VALUES ('Пупа', '2011-01-01', 'кс-кс-кс', 1),
       ('Олег', '2016-01-01', "отставить!", 1),  
       ('Тьма', '2017-01-01', "", 1);

INSERT INTO dogs (Name, Birthday, Commands, Genus_id)
VALUES ('Дик', '2020-01-01', 'ко мне, лежать, лапу, голос', 2),
       ('Граф', '2021-06-12', "сидеть, лежать, лапу", 2),
       ('Шарик', '2018-05-01', "сидеть, лежать, лапу, след, фас", 2),
       ('Босс', '2021-05-10', "сидеть, лежать, фу, место", 2);

INSERT INTO hamsters (Name, Birthday, Commands, Genus_id)
VALUES ('Малой', '2020-10-12', '', 3),
       ('Медведь', '2021-03-12', "атака сверху", 3),
       ('Ниндзя', '2022-07-11', NULL, 3),
       ('Бурый', '2022-05-10', NULL, 3);

INSERT INTO horses (Name, Birthday, Commands, Genus_id)
VALUES ('Гром', '2020-01-12', 'бегом, шагом', 1),
       ('Закат', '2017-03-12', "бегом, шагом, хоп", 1),
       ('Байкал', '2016-07-12', "бегом, шагом, хоп, брр", 1),
       ('Молния', '2020-11-10', "бегом, шагом, хоп", 1);

INSERT INTO donkeys (Name, Birthday, Commands, Genus_id)
VALUES ('Первый', '2019-04-10', NULL, 2),
       ('Второй', '2020-03-12', "", 2),
       ('Третий', '2021-07-12', "", 2),
       ('Четвертый', '2022-12-10', NULL, 2);

INSERT INTO camels (Name, Birthday, Commands, Genus_id)
VALUES ('Горбатый', '2022-04-10', 'вернись', 3),
       ('Самец', '2019-03-12', "остановись", 3),
       ('Сифон', '2015-07-12', "повернись", 3),
       ('Борода', '2022-12-10', "улыбнись", 3);
```
Результат:

![Screenshot](/source/s14.png)

10. Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой
    питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.

```sql
SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;

SELECT Name, Birthday, Commands FROM horses
UNION SELECT  Name, Birthday, Commands FROM donkeys;
```
Результат:

![Screenshot](/source/s15.png)
    
11. Создать новую таблицу “молодые животные” в которую попадут все
    животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
    до месяца подсчитать возраст животных в новой таблице

```sql
CREATE TEMPORARY TABLE animals AS 
SELECT *, 'Лошади' as genus FROM horses
UNION SELECT *, 'Ослы' AS genus FROM donkeys
UNION SELECT *, 'Собаки' AS genus FROM dogs
UNION SELECT *, 'Кошки' AS genus FROM cats
UNION SELECT *, 'Хомяки' AS genus FROM hamsters;

CREATE TABLE young_animal AS
SELECT Name, Birthday, Commands, genus, TIMESTAMPDIFF(MONTH, Birthday, CURDATE()) AS Age_in_month
FROM animals WHERE Birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
 
SELECT * FROM young_animal;
``` 
Результат:

![Screenshot](/source/s16.png)

12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на
    прошлую принадлежность к старым таблицам.

```sql
SELECT h.Name, h.Birthday, h.Commands, pack.Genus_name, young.Age_in_month 
FROM horses h
LEFT JOIN young_animal young ON young.Name = h.Name
LEFT JOIN packed_animals pack ON pack.Id = h.Genus_id
UNION 
SELECT d.Name, d.Birthday, d.Commands, pack.Genus_name, young.Age_in_month 
FROM donkeys d 
LEFT JOIN young_animal young ON young.Name = d.Name
LEFT JOIN packed_animals pack ON pack.Id = d.Genus_id
UNION
SELECT c.Name, c.Birthday, c.Commands, home.Genus_name, young.Age_in_month 
FROM cats c
LEFT JOIN young_animal young ON young.Name = c.Name
LEFT JOIN home_animals home ON home.Id = c.Genus_id
UNION
SELECT d.Name, d.Birthday, d.Commands, home.Genus_name, young.Age_in_month 
FROM dogs d
LEFT JOIN young_animal young ON young.Name = d.Name
LEFT JOIN home_animals home ON home.Id = d.Genus_id
UNION
SELECT hm.Name, hm.Birthday, hm.Commands, home.Genus_name, young.Age_in_month 
FROM hamsters hm
LEFT JOIN young_animal young ON young.Name = hm.Name
LEFT JOIN home_animals home ON home.Id = hm.Genus_id;
```
Результат:

![Screenshot](/source/s17.png)

13. Создать класс с Инкапсуляцией методов и наследованием по диаграмме.

14. Написать программу, имитирующую работу реестра домашних животных.
    В программе должен быть реализован следующий функционал:

    14.1 Завести новое животное  
    14.2 определять животное в правильный класс  
    14.3 увидеть список команд, которое выполняет животное  
    14.4 обучить животное новым командам  
    14.5 Реализовать навигацию по меню  

15. Создайте класс Счетчик, у которого есть метод add(), увеличивающий̆
    значение внутренней̆ int переменной на 1 при нажатие “Завести новое
    животное” Сделайте так, чтобы с объектом такого типа можно было работать в
    блоке try-with-resources. Нужно бросить исключение, если работа с объектом
    типа счетчик была не в ресурсном try и/или ресурс остался открыт. Значение
    считать в ресурсе try, если при заведения животного заполнены все поля.