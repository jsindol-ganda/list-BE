FROM eclipse-temurin:17-jdk-alpine AS BUILD_IMAGE
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . /app
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

FROM eclipse-temurin:17-jdk-alpine
RUN mkdir /app_prod
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
COPY --from=BUILD_IMAGE /app/target/mylist-test-0.0.1-SNAPSHOT.jar /app_prod/mylist-test-0.0.1-SNAPSHOT.jar
WORKDIR /app_prod
RUN chown -R javauser:javauser /app_prod
USER javauser
EXPOSE 8080
CMD "java" "-jar" "mylist-test-0.0.1-SNAPSHOT.jar"