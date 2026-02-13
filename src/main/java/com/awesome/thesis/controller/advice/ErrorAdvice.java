package com.awesome.thesis.controller.advice;

import com.awesome.thesis.logic.application.exceptions.SiteNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Advice für Error Catching.
 */
@ControllerAdvice
public class ErrorAdvice {
  /**
   * Exception Handler für nicht vorhandene Seiten.
   *
   * @param e Exception
   * @return ModelAndView mit html siteNotFound
   */
  @ExceptionHandler(SiteNotFoundException.class)
  public ModelAndView profilLocking(SiteNotFoundException e) {
    ModelAndView mav = new ModelAndView("siteNotFound");
    mav.addObject("errorMessage", e.getMessage());
    return mav;
  }
}
