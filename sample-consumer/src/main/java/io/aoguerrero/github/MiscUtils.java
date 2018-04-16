package io.aoguerrero.github;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiscUtils {

	static final Logger logger = LoggerFactory.getLogger(MiscUtils.class);

	public static Properties loadProperties() {
		String homeDir = System.getProperty("user.dir");
		String configPath = homeDir + File.separator + "config.properties";
		try {
			FileInputStream input = new FileInputStream(new File(configPath));
			Properties props = new Properties();
			props.load(input);
			input.close();
			return props;
		} catch (IOException ioe) {
			logger.error(MessageFormat.format("Ocurrio un error cargando el archivo \"{0}\"", configPath), ioe);
			return null;
		}
	}
}
