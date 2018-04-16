package io.aoguerrero.github;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { KafkaAutoConfiguration.class })
public class MainProducer {

	static final Logger logger = LoggerFactory.getLogger(MiscUtils.class);

	public static void main(String[] args) throws Exception {
		Properties config = MiscUtils.loadProperties();
		Properties kafkaProps = new Properties();

		kafkaProps.put("bootstrap.servers", config.getProperty("kafka.server"));
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("acks", "1");
		kafkaProps.put("retries", "3");
		kafkaProps.put("linger.ms", 5);

		Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);

		for (int i = 1; i <= 5; i++) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(
					config.getProperty("kafka.topic"), "{\"value\": " + i + "}");
			producer.send(record).get();
		}
		producer.close();
	}
}
