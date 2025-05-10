# Maven + JDK 17 のビルドステージ
FROM maven:3.9.6-eclipse-temurin AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# JRE 17 の実行ステージ（alpineではなくDebianベースで安定）
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
