
CREATE MEMORY TABLE Product (
       id bigint identity not null primary key,
       name varchar(255), category varchar(255),
       amount integer,
       unit varchar(255),
       purchaseprice decimal,
       sellprice decimal,
       description varchar(255));

INSERT INTO Product (NAME, CATEGORY, AMOUNT, UNIT, PURCHASEPRICE, SELLPRICE, DESCRIPTION)
VALUES ('Alma','Gyümölcs',20,'kg',10,200,'Magyar alma');
INSERT INTO Product (NAME, CATEGORY, AMOUNT, UNIT, PURCHASEPRICE, SELLPRICE, DESCRIPTION)
VALUES ('Körte','Gyümölcs',4,'kg',60,2000,'Magyar Körte');
INSERT INTO Product (NAME, CATEGORY, AMOUNT, UNIT, PURCHASEPRICE, SELLPRICE, DESCRIPTION)
VALUES ('Barack','Gyümölcs',3,'kg',40,100,'Magyar Barack');
INSERT INTO Product (NAME, CATEGORY, AMOUNT, UNIT, PURCHASEPRICE, SELLPRICE, DESCRIPTION)
VALUES ('Csirke','Hús',10,'kg',20,50,'Csirke');

CREATE ROLE "DEV";
GRANT DEV TO SA;

GRANT SELECT ON  Product TO DEV;
