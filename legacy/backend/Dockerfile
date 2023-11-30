FROM gradle:jdk17-alpine
WORKDIR /backend

COPY gradlew .
COPY gradle gradle

RUN chmod +x ./gradlew
RUN ./gradlew --version

COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew build -x test

CMD ["java", "-jar", "build/libs/simple-blog-0.1.0-SNAPSHOT.jar"]
