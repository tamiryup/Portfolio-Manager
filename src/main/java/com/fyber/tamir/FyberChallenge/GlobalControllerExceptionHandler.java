package com.fyber.tamir.FyberChallenge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import error_messages.ErrorMessage;
import exceptions.InvalidClientException;
import exceptions.InvalidPortfolioException;
import exceptions.InvalidRecommendationStrategyException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidPortfolioException.class)
	@ResponseBody
	public ErrorMessage handleInvalidPortfolio() {
		return new ErrorMessage("Invalid Portfolio Request", "The amounts should be unnegative and the stocks should be known "
				+ "also cannot sell more stocks than currently owned");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidClientException.class)
	@ResponseBody
	public ErrorMessage handleInvalidClient() {
		return new ErrorMessage("Invalid Client", "The client does not exist");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidRecommendationStrategyException.class)
	@ResponseBody
	public ErrorMessage handleInvalidRecommendation() {
		return new ErrorMessage("Invalid Recommendation Strategy", "The recommendation strategies are: performance, stable, best");
	}
	
}
