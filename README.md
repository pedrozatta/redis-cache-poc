# Teste de Cache com Redis

docker run -it --name redis -p 6379:6379   redis:5.0.3
    
    
## Execução
export PORT=8080
mvn spring-boot:run

export PORT=8081
mvn spring-boot:run
