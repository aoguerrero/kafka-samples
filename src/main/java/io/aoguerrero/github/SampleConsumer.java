package io.aoguerrero.github;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleConsumer {

	static final Logger logger = LoggerFactory.getLogger(Config.class);

	public void run() throws Exception {
		Properties config = Config.loadProperties();

		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", config.getProperty("kafka.server"));
		kafkaProps.setProperty("group.id", config.getProperty("kafka.group"));
		kafkaProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("enable.auto.commit", "true");

		@SuppressWarnings("resource")
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps);

		consumer.subscribe(Collections.singletonList(config.getProperty("kafka.topic")));

		logger.info("Started consumer for topic \"" + config.getProperty("kafka.topic") + "\".");

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);

			for (ConsumerRecord<String, String> record : records) {
				String json = "";
				try {
					json = record.value();
					logger.debug("Incomming message \"" + json + "\"");
				} catch (Exception e) {
					logger.error("Error processing message \"" + json + "\"", e);
				}

			}
		}
	}

}
