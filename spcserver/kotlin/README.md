- Access: https://start.spring.io/

![image](https://github.com/user-attachments/assets/d35930df-2106-4393-952e-c3b31342a4c7)

- H2 Access

```yaml
  datasource:
    url: jdbc:h2:file:./data/mydb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
    driver-class-name: org.h2.Driver
    username: sa
    password: 123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console:
      enabled: true
```

![image](https://github.com/user-attachments/assets/c8457233-ad9a-47d6-8aee-91b116f30738)

- JDBC URL: jdbc:h2:file:./data/mydb
- Username: sa
- Password: password
