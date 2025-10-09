FROM openjdk:17-jdk-slim

# Configurar locale e encoding
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Instalar ferramentas necessárias
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Dar permissão ao gradlew
RUN chmod +x gradlew

# Copiar código fonte
COPY src src

# Build da aplicação com encoding UTF-8
RUN ./gradlew build -x test -Dfile.encoding=UTF-8

# Expor porta
EXPOSE 8080

# Comando para executar com encoding UTF-8
CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "build/libs/mundo-star-wars-0.0.1-SNAPSHOT.jar"]