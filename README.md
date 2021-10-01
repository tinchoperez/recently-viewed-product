# recently-viewed-products
This repository contains the code and the way to run and test Recently Viewed Products for a customer exercise.

# Viewed Product Project

I decided to create the Microservice using Spring Boot and Data JPA connecting to an inmemory relational database (H2) as Spring Initializer makes some configuration easier to have a basic runing Microservice.
It includes a simple Controller test for each operation.

Data schema is:
CREATE TABLE CUSTOMER(ID INTEGER PRIMARY KEY, NAME VARCHAR(255))
CREATE TABLE PRODUCT(ID INT PRIMARY KEY, PRODUCT_DESCRIPTION VARCHAR(255),  PRODUCT_NAME VARCHAR(255))
CREATE TABLE VIEWED_PRODUCT(ID INT PRIMARY KEY, CUSTOMER_ID INTEGER NOT NULL, PRODUCT_ID INTEGER NOT NULL, VIEWED_DATE TIMESTAMP)

Instructions to execute the Microservice and test it.
Attached is the Collection of Postman Request ready to be imported in order to execute operations implemented in the Microservice.

Download code and execute RecentlyviewedprodApplication which is the entry point for Spring Boot application.

Test Cases:
Once application is started it will authomatically load some test data in database:
- There will be 3 Customers (Customer with id 3 will insert viewed products with a date with 7 days before in order to test list viewed products after X days)
- There will be 10 products loaded to simulate the products that a customer can view.

You can see the schema and the result of execution of each operation in the database easily connecting to http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:prodhistory)

1 - Test Insert viewed product:
Simulating that the microservice is called to insert a viewed product you can execute a POST operation http://localhost:8080/api/v1/rvp/saveProduct/1/6 to indicate that customer with id 1 viewed product with id 6.
In this way it can be changed for customer from id 1 to 3 and prodcuts from id 1 to 1o and will be asociating customer id with product id and saving the date and time of the viewed product in VIEWED_PRODUCT table.

2 - Test Delete viewed prodcut:
Executing the DELETE operation http://localhost:8080/api/v1/rvp/deleteProduct/1/6 allow to delete, in this example, delete viewed prodcut with id 6 from viewed prodcut list of customer 1.

3 - Test Update viewed prodcut date and time:
If the customer view a product already viewed, then the PUT operation http://localhost:8080/api/v1/rvp/updateProduct/3/4 will update the date and time of the viewed product.
To see clearally how this operation works please execute  a POST operation http://localhost:8080/api/v1/rvp/saveProduct/3/4 before to execute PUT http://localhost:8080/api/v1/rvp/updateProduct/3/4, in this way
, as mentioned in notes customer with id 3 will insert a date 5 days before today to simulate that he/she viewed products some time ago. After execution of PUT  http://localhost:8080/api/v1/rvp/updateProduct/3/4 will
update the date and time to today.

4 - Test Get list of viewed prodcuts for a customer:
Here we have 2 cases to test:
1 - Customer did not see products before: If Customer 1, for instance, did not view products recently then executing GET operation like http://localhost:8080/api/v1/rvp/viewedProducts/1/3 (this mean that for customer 1 will try to get the list of products of last 3 days) then
will get a default list of prodcuts.
2 - Customer have a list of viewed products: Please execute POST  http://localhost:8080/api/v1/rvp/saveProduct/1/6 operation for a customer with different product ids then executing operation http://localhost:8080/api/v1/rvp/viewedProducts/1/3 will see the list of viewed
prodcuts in last 3 days. If you also insert some viewed prodcuts for customer 3 (insert with previous 5 days viewed product) you can see clearly how the filter of days work.

Notes: For sure there are several other things to take into account, checks or validations to do to improve it.

