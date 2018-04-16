package io.aoguerrero.github;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleConsumer {

	static final Logger logger = LoggerFactory.getLogger(MiscUtils.class);

	private KafkaConsumer<String, String> consumer;

	public void run() throws Exception {
		Properties config = MiscUtils.loadProperties();

		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", config.getProperty("kafka.server"));
		kafkaProps.setProperty("group.id", config.getProperty("kafka.group"));
		kafkaProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.setProperty("enable.auto.commit", "true");

		this.consumer = new KafkaConsumer<String, String>(kafkaProps);

		final Thread mainThread = Thread.currentThread();
		final SampleConsumer main = new SampleConsumer();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					main.consumer.wakeup();
					mainThread.join();
				} catch (InterruptedException e) {
				}
			}
		});

		main.consumer.subscribe(Collections.singletonList(config.getProperty("kafka.topic")));

		logger.info("Started consumer for topic \"" + config.getProperty("kafka.topic") + "\".");

		while (true) {
			ConsumerRecords<String, String> records = main.consumer.poll(100);

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
