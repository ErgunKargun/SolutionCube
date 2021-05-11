package com.solutioncube.helper;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.solutioncube.common.IAsyncExecutableTaskFunc;
import com.solutioncube.pojo.TaskParameter;

@Component
public class AsyncHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncHelper.class);
	
	@Async
	public Future<Boolean> execAsync(IAsyncExecutableTaskFunc asynExecutableTaskFunc, TaskParameter taskParameter) {

		asynExecutableTaskFunc.execAsync(taskParameter);
		return new AsyncResult<Boolean>(true);
	}

	public void waitTillEndOfSynchronizedFunc(Collection<Future<Boolean>> futures) {

		futures.forEach(future -> {
			
			try {
				
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				
				logger.error("Error while running async threads. Exception: " + e.getMessage());
			}
		});
	}
}