package io.aoguerrero.github;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleProducer {

	static final Logger logger = LoggerFactory.getLogger(Config.class);

	public void run() throws Exception {
		Properties config = Config.loadProperties();
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
