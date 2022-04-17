# axon-es-cqrs-kt
Sample event sourcing/cqrs app implemented using Kotlin and Axon framework

For more information refer to [Axon Architecture Overview](https://docs.axoniq.io/reference-guide/architecture-overview)

### How to run
This app requires JDK 11. If your default JDK is `11`, you can run:
```shell
$ ./mvnw clean spring-boot:run
```
or if you have multiple Java versions installed:
```shell
$ JAVA_HOME='/Library/Java/JavaVirtualMachines/jdk-11.0.13.jdk/Contents/Home' ./mvnw clean spring-boot:run
```

### Open an account
```shell
$ curl -X POST -H "Content-Type: application/json" \
-d '{"accountName": "test"}' \
http://localhost:8080/accounts/
```

### List all accounts
```shell
$ curl http://localhost:8080/accounts/
```
Sample response:
```json
[
  {
    "accountId": "060fde83-2600-4a9a-ba2f-f5a917539fa0",
    "accountName": "test",
    "amount": 0
  }
]
```

### Deposit into account (twice)
```shell
$ curl -X POST \
http://localhost:8080/accounts/060fde83-2600-4a9a-ba2f-f5a917539fa0/deposit/5


$ curl -X POST \
http://localhost:8080/accounts/060fde83-2600-4a9a-ba2f-f5a917539fa0/deposit/15
```
Result:
```json
[
  {
    "accountId": "060fde83-2600-4a9a-ba2f-f5a917539fa0",
    "accountName": "test",
    "amount": 20
  }
] 
```

### Explore data in database and event store
Navigate to http://localhost:8080/h2-console/ with the following inputs:

| Input     | Value                |
|:----------|:---------------------|
| JDBC URL  | `jdbc:h2:mem:testdb` |
| User Name | `sa`                 |
| Password  | `password`           |

#### Projection table
Has a single record which is the final state:

```sql
SELECT * FROM ACCOUNT_ENTITY;
```
| ID                                   | 	AMOUNT | 	NAME |
|:-------------------------------------|:--------|:------|
| 060fde83-2600-4a9a-ba2f-f5a917539fa0 | 20	     | test  |

#### Event store table
Has 3 events as the immutable log:

```sql
SELECT * FROM DOMAIN_EVENT_ENTRY;
```
_For clarity/brevity sake, the `META_DATA` and `PAYLOAD` columns are removed from the result_

| GLOBAL_INDEX | 	EVENT_IDENTIFIER  	                   | 	  	PAYLOAD_REVISION | 	PAYLOAD_TYPE                             | 	TIME_STAMP               | 	AGGREGATE_IDENTIFIER                  | 	SEQUENCE_NUMBER | 	TYPE   | 
|:-------------|:---------------------------------------|:---------------------|:------------------------------------------|:--------------------------|:---------------------------------------|:-----------------|:--------|
| 1	           | de2a0479-607f-497d-8aef-342eaa78b31c		 | 	null                | 	com.example.demo.events.AccountOpened	   | 2022-04-17T09:36:27.174Z	 | 060fde83-2600-4a9a-ba2f-f5a917539fa0	  | 0	               | Account |
| 2	           | a45d8c72-d90f-4f76-8b7b-e5f0cf0e858e		 | 	null                | 	com.example.demo.events.AmountDeposited	 | 2022-04-17T09:37:43.224Z	 | 060fde83-2600-4a9a-ba2f-f5a917539fa0	  | 1	               | Account |
| 3	           | be24c69b-c1e7-4d16-b6f8-9ca9fe817465	  | 		null	              | com.example.demo.events.AmountDeposited   | 	2022-04-17T09:37:48.148Z | 	060fde83-2600-4a9a-ba2f-f5a917539fa0	 | 2	               | Account |