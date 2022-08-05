FROM openjdk:8
RUN apt update
RUN apt install git
RUN git clone https://github.com/luizfeliperomero/Buservation
WORKDIR /Buservation/Buservation/src/main/java
RUN javac webServer/Server.java
ENTRYPOINT ["java", "webServer.Server"]
