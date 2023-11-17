CREATE MEMORY TABLE products (
       id bigint identity not null primary key,
       name varchar(255), category varchar(255),
       amount integer,
       unit varchar(255),
       purchaseprice decimal,
       sellprice decimal,
       description varchar(255));

INSERT INTO PUBLIC.products (NAME, CATEGORY, AMOUNT, UNIT, PURCHASEPRICE, SELLPRICE, DESCRIPTION)
VALUES ('Alma','Gyümölcs',3,'kg',100,200,'Magyar alma');

CREATE ROLE "DEV";
GRANT DEV TO "SA";

GRANT SELECT ON  PUBLIC.PRODUCTS TO DEV;
