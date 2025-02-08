# ベースイメージとしてOpenJDK 21を使用
FROM openjdk:21-jdk-slim

# アプリケーションのJARファイルをコンテナにコピー
COPY target/spotify-mock-api-0.0.1-SNAPSHOT.jar app.jar

# アプリケーションの実行ポートを指定
EXPOSE 8081

# アプリケーションの起動コマンド
ENTRYPOINT ["java", "-jar", "/app.jar"]
