# opensns betermetelkaar ssolaunch samenbeter emulator 

The emulator to test the launch from both the consumer and producer side. 

## Building and running with Java

### Prerequisies
* Java
* Maven

### Running

```bash
mvn clean install spring-boot:run

```


## Building and running with Docker
### Prerequisies
* Docker

### Running

```bash
docker build -t sns-emulator .
docker run sns-emulator
```

# Testing

* Testing the launch as producer: [http://localhost:8080/index.html](http://localhost:8080/index.html)
* Testing the launch as consumer: [http://localhost:8080/validate.html](http://localhost:8080/validate.html)


