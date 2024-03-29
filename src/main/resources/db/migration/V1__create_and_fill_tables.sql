-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspection

DROP TABLE IF EXISTS Cars_of_people;
DROP TABLE IF EXISTS Person_data;
DROP TABLE IF EXISTS Email_templates;
DROP TABLE IF EXISTS Cars;

CREATE TABLE IF NOT EXISTS Email_templates (
    language_id int NOT NULL,
    text VARCHAR(2000),
    CONSTRAINT PK_Email_templates PRIMARY KEY (language_id)
    );


CREATE TABLE IF NOT EXISTS Person_data (
    person_id int NOT NULL,
    name VARCHAR(128) NOT NULL,
    date_of_birth DATE NOT NULL,
    country VARCHAR(128) NOT NULL,
    language_id int NOT NULL,
    CONSTRAINT PK_Person_data PRIMARY KEY (person_id),
    CONSTRAINT FK_Person_data_language_id FOREIGN KEY (language_id) REFERENCES email_templates (language_id)
    );

CREATE TABLE IF NOT EXISTS Cars (
    car_id int NOT NULL,
    brand VARCHAR (32) NOT NULL,
    type VARCHAR (32) NOT NULL,
    plate_number VARCHAR (16) NOT NULL,
    year_of_manufacture int NOT NULL,
    calculated_value int NOT NULL,
    driven_distance int NULL,
    is_sent BOOLEAN NOT NULL,
    CONSTRAINT PK_Cars PRIMARY KEY (car_id)
    );

CREATE TABLE IF NOT EXISTS Cars_of_people (
    person_id int NOT NULL,
    car_id int NOT NULL,
     CONSTRAINT PK_Cars_of_people PRIMARY KEY (person_id,car_id),
     CONSTRAINT FK_Cars_of_people_person_id FOREIGN KEY (person_id) REFERENCES Person_data (person_id),
     CONSTRAINT FK_Cars_of_people_car_id FOREIGN KEY (car_id) REFERENCES Cars (car_id)
);



INSERT INTO Email_templates (language_id, text) VALUES (1, 'Dear <name> (country: <country>, date of birht: <dateOfBirth>)!

You can read here the description of your uploaded cars:

<carsLoopBegin>

Brand: <brand>
Type: <type>
Plate number: <plateNumber>
Year of manufacture: <yearOfManufacture>
Driven distance (km): <drivenDistance>

Based on the data above our system considers the following market price that suits to your car: <calculatedValue> Euro.

<carsLoopEnd>

Thank you for using our services!

Kindest regards,

Team CarEvaluator
');

INSERT INTO Email_templates (language_id, text) VALUES (2, 'Kedves <name> (ország: <country>, születési idő: <dateOfBirth>)!

Az ön által feltöltött autók jellemzését alább olvashatja:

<carsLoopBegin>

Márka: <brand>
Típus: <type>
Rendszám: <plateNumber>
Gyártási év: <yearOfManufacture>
Megtett kilométer: <drivenDistance>

A fenti adatok alapján rendszerünk a következő piaci értéket tartja reálisnak az Ön autója esetén: <calculatedValue> Euro.

<carsLoopEnd>

Köszönjük, hogy igénybe vette szolgáltatásunkat!

Üdvözlettel,
A CarEvaluator csapata
');

DELETE FROM Person_data;
INSERT INTO Person_data (person_id, name, date_of_birth, country, language_id) VALUES (1, 'Jake Greenfield', '1974-08-15', 'United Kingdom', 1);
INSERT INTO Person_data (person_id, name, date_of_birth, country, language_id) VALUES (2, 'Horváth Hedvig', '1982-02-19', 'Hungary', 2);
INSERT INTO Person_data (person_id, name, date_of_birth, country, language_id) VALUES (3, 'Erwin Lefavre', '1969-11-05', 'France', 1);

DELETE FROM Cars;

    INSERT INTO Cars (car_id, brand, type, plate_number, year_of_manufacture, calculated_value, driven_distance, is_sent)
VALUES (1, 'Opel', 'Vectra', 'UK 123 45678', 2008, 11140, 125000, false);
INSERT INTO Cars (car_id, brand, type, plate_number, year_of_manufacture, calculated_value, driven_distance, is_sent)
VALUES (2, 'Mini', 'Cooper', 'UK 456 12345', 2015, 0, 10000, false);

INSERT INTO Cars (car_id, brand, type, plate_number, year_of_manufacture, calculated_value, driven_distance, is_sent)
VALUES (3, 'Suzuki', 'Swift', 'MTK 128', 2014, 12295, 26000, true);


INSERT INTO Cars (car_id, brand, type, plate_number, year_of_manufacture, calculated_value, driven_distance, is_sent)
VALUES (4, 'Peugeot', '206', 'FR 4567 TT', 2004, 3900, 195700, false);

INSERT INTO Cars (car_id, brand, type, plate_number, year_of_manufacture, calculated_value, driven_distance, is_sent)
VALUES (5, 'Citroen', 'C4 cactus', 'FR 8912 CC', 2014, 15750, 36500, true);



DELETE FROM Cars_of_people;
INSERT INTO Cars_of_people (person_id, car_id) VALUES (1,1);
INSERT INTO Cars_of_people (person_id, car_id) VALUES (1,2);
INSERT INTO Cars_of_people (person_id, car_id) VALUES (2,3);
INSERT INTO Cars_of_people (person_id, car_id) VALUES (3,4);
INSERT INTO Cars_of_people (person_id, car_id) VALUES (3,5);
