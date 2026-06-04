FROM gradle:8.5-jdk17
WORKDIR /app
COPY . .
RUN gradle build -x test --no-daemon
EXPOSE 7860
ENV PORT=7860
ENV SERVER_PORT=7860
ENTRYPOINT ["gradle", "bootRun", "--no-daemon"]