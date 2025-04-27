sudo docker run -d \
        -p 5432:5432 \
        -e POSTGRES_PASSWORD=mysecretpassword \
        postgres

mvn liquibase:update