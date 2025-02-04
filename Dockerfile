# ベースイメージとしてOpenJDK 17を使用
FROM openjdk:17-jdk-slim

# アプリケーションのJARファイルをコンテナにコピー
ARG JAR_FILE=spotify-mock-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# アプリケーションの実行ポートを指定
EXPOSE 8081

# アプリケーションの起動コマンド
ENTRYPOINT ["java", "-jar", "/app.jar"]
