FROM gradle:jdk21
WORKDIR /thesis
COPY . /thesis
RUN gradle bootJar

CMD java -jar /thesis/build/libs/thesis-0.0.1-SNAPSHOT.jar