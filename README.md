# TradingApi

This is a small bank/trading application and the purpose of the application is to execute orders when the price is right. 

This programs expects to have a running price application on localhost with the following endpoint: /btc-price
The programs polls prices from this endpoint every 5s in a scheduled task and then puts the prices in a JMS queue. 

The application has four endpoints:
- account/create that expect parameters name, balance to open a new account
- account/{id} to fetch information of an acocunt
- order/create that expect parameters accountId, priceLimit, amount to create a limit order
- order/{id} to fetch orders

Here are some curl commands to test the application:
```
curl -X POST 'http://localhost:8080/account/create?name=Stina&balance=5000'

curl 'http://localhost:8080/account/1'

curl -X POST 'http://localhost:8080/order/create?accountId=1&priceLimit=3000.2555&amount=2'

curl 'http://localhost:8080/order/1'
```

## Database
H2 was used as an embedded database for this project. 

## How to build
The program can be built with maven: 
* `mvn clean install` (without tests)

## How to run
The program can be run with maven:  
`mvn org.springframework.boot:spring-boot-maven-plugin:run
