package com.solutioncube.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutioncube.common.IService;
import com.solutioncube.common.TaskType;
import com.solutioncube.helper.ServiceRunner;

@Service
public class SolutionCubeJobService {

	private static final Logger logger = LoggerFactory.getLogger(SolutionCubeJobService.class);

	@Autowired
	private ServiceRunner serviceRunner;

	@Autowired
	private IService erisyemService;
	
	@Autowired
	private IService vanucciService;
	
	private List<IService> registerServices() {

		return Arrays.asList(new IService[] {
				
				erisyemService
				,vanucciService
		});
	}

	public void runDailySolutionCubeJob(boolean isAsync) {

		serviceRunner.runServices(registerServices(), TaskType.TASKS_WHICH_DAILY, isAsync);	
		logger.info("SolutionCubeJobService finished running.");	
	}
}