package com.solutioncube.collection;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solutioncube.common.ITask;
import com.solutioncube.common.Task;
import com.solutioncube.pojo.Parameter;

public class EnergyMeasurementsHistoryReport implements ITask {

	private static final Logger logger = LoggerFactory.getLogger(EnergyMeasurementsHistoryReport.class);
	private final String BASE_COLLECTION_NAME = "EnergyMeters";
	private final String COLLECTION_NAME = this.getClass().getSimpleName();
	private final String URI = "https://api.triomobil.com/facility/v1/reports/energy/measurements/history?_sortOrder=ASC&energyMeterId=%s&ts.since=%s&_limit=5000";
  
	@Override
	public void execute(Parameter parameter) {
		
		logger.info("Execution Started");
		List<String> energyMeters = parameter.getMongoTemplate().findAll(String.class, BASE_COLLECTION_NAME);
		logger.info("EnergyMeters Size: "+energyMeters.size());
		for (String energyMeter : energyMeters) {
			
			JSONObject energyMetersJSONObject = new JSONObject(energyMeter);
			String energyMeterId = energyMetersJSONObject.getString("_id");
			parameter.setId(energyMeterId);
			parameter.setIdColumnName("energyMeterId");
			parameter.setUri(String.format(URI, energyMeterId, parameter.getSinceDateAsString()));
			parameter.setCollectionName(COLLECTION_NAME);
			new Task().execute(parameter);
			parameter.setId(null);
			parameter.setIdColumnName(null);
		}		
		logger.info("Execution Done");
	}

	@Override
	public String getCollectionName() {
		return COLLECTION_NAME;
	}
}