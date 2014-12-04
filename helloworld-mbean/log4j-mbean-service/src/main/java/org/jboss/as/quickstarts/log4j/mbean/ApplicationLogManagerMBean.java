package com.amadeus.log4j;


import java.util.Map;

import javax.management.MXBean;

@MXBean
public interface ApplicationLogManagerMBean {

	void setLogLevel(String logger, String level);
	
	Map<String, String> getLogLevels();
	
}
