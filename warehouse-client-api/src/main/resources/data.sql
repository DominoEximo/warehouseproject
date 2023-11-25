
CREATE MEMORY TABLE Product (
       id bigint identity not null primary key,
       name varchar(255), category varchar(255),
       amount integer,
       unit varchar(255),
       purchaseprice decimal,
       sellprice decimal,
       description varchar(255));

CREATE MEMORY TABLE Category (
       id bigint identity not null primary key,
       categoryName varchar(255));

CREATE MEMORY TABLE Unit (
       id bigint identity not null primary key,
       unitName varchar(255));


INSERT INTO Category (categoryName) values 'Hus';
INSERT INTO Category (categoryName) values 'Valami';
INSERT INTO Category (categoryName) values 'Ruha';
INSERT INTO Category (categoryName) values 'Gyumolcs';

INSERT INTO Unit (unitName) values 'kg';
INSERT INTO Unit (unitName) values 'lbs';
