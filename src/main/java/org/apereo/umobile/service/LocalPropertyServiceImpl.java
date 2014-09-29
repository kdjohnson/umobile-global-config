package org.apereo.umobile.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.LinkedHashMap;
import java.util.Enumeration;

import org.springframework.stereotype.Service;

@Service
public class LocalPropertyServiceImpl implements IPropertiesService {

	private final ClassLoader classLoader = getClass().getClassLoader();
	private final List<String> ARRAY_PROPERTY_LIST = Arrays.asList("disabledPortlets");
	private Properties props;

	public boolean loadPropertiesFile(String fileName) {
		if (props == null) {
 			props = new Properties();
		}

		try {
			final File file = new File(classLoader.getResource(fileName).getFile());
			props.load(new FileInputStream(file));
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}

		return true;
	}

	public Object getProperty(String property) {
		Object temp;

		if(ARRAY_PROPERTY_LIST.contains(property)) {
			final String arrayProperties = props.getProperty(property);
			temp = Arrays.asList(arrayProperties.split(","));
		} else {
			temp = props.getProperty(property);
		}

		return temp;
	}

	public Map<String, Object> getAllProperties() {
		final Map<String, Object> map = new LinkedHashMap<String, Object>();

		for (String key : props.stringPropertyNames()) {
			map.put(key, getProperty(key));
		}

		return map;
	}

	private boolean isFileLoaded() {
		return props != null;
	}

	/*
	public Map<String, Object> getAllProperties() {
		final Map<String, Object> map = new LinkedHashMap<String, Object>();
		final Enumeration e = props.propertyNames();
		while( e.hasMoreElements() ) {
			final String key = (String) e.nextElement();

			if(ARRAY_PROPERTY_LIST.contains(key)) {
				final String arrayProperties = props.getProperty(key);
				map.put(key, Arrays.asList(arrayProperties.split(",")));
			} else {
				map.put(key, props.getProperty(key));
			}

		}

		return map;
	}
	*/
}