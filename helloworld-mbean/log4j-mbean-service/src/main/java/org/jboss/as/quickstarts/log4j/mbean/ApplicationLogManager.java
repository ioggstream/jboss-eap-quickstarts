package com.amadeus.log4j;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ApplicationLogManager implements ApplicationLogManagerMBean {

	@SuppressWarnings("unchecked")
	public Map<String, String> getLogLevels() {
		HashMap<String, String> result = new HashMap<String, String>();
		Enumeration<Logger> e = Logger.getRootLogger().getLoggerRepository().getCurrentLoggers();
		while (e.hasMoreElements()) {
			Logger l = e.nextElement();
			if (l.getLevel() != null) {
				result.put(l.getName(), l.getLevel().toString());
			}
		}
		return result;
	}

	public void setLogLevel(String logger, String level) {
		Logger l = Logger.getLogger(logger);
		l.setLevel(Level.toLevel(level));
	}

}
