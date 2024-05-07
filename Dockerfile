FROM eclipse-temurin:17-jdk-alpine AS BUILD_IMAGE
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
RUN mkdir /app_prod
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
COPY --from=BUILD_IMAGE /app/target/mylist-test-0.0.1-SNAPSHOT.jar /app_prod/mylist-test-0.0.1-SNAPSHOT.jar
WORKDIR /app_prod
RUN chown -R javauser:javauser /app
USER javauser
CMD "java" "-jar" "mylist-test-0.0.1-SNAPSHOT.jar"