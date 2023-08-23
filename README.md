# IMPORTANT: THIS PROJECT USES DEPRECATED LIBRARIES AND IS NOT MAINTAINED

Generar paquete

    mvn clean package

Ejecutar

    nohup java -jar target/kafka-samples.jar --consumer &> 1.log&
    nohup java -jar target/kafka-samples.jar --consumer &> 2.log&  
    nohup java -jar target/kafka-samples.jar --consumer &> 3.log&  
    nohup java -jar target/kafka-samples.jar --consumer &> 4.log&
  
    java -jar target/kafka-samples.jar --producer
  
    tail -f *.log
  
