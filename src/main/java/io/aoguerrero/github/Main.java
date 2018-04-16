package io.aoguerrero.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	static final Logger logger = LoggerFactory.getLogger(MiscUtils.class);

	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			String firstParam = args[0].trim().toLowerCase();
			if (firstParam.equals("--producer")) {
				SampleProducer producer = new SampleProducer();
				producer.run();
			} else if (firstParam.equals("--consumer")) {
				SampleConsumer consumer = new SampleConsumer();
				consumer.run();
			}
		}
		logger.error("Invalid parameters");
	}

}
