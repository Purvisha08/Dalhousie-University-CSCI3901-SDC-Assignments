CREATE TABLE purchases (
  purchaseid int,
  productid int,
  supplierid int,
  purchasedate varchar(255),
  unitprice decimal(10,4) DEFAULT 0.0000,
  quantity int,
  shipvia int,
  shipdate date,
  shipid int,
  receivedate date,
  PRIMARY KEY (purchaseid, productid),
  FOREIGN KEY (productid) REFERENCES products(productid),
  FOREIGN KEY (supplierid) REFERENCES products(supplierid)
);












