package com.springboot.app;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

 @Bean
 public FilterRegistrationBean < SessionFilter > filterRegistrationBean() {
  FilterRegistrationBean <SessionFilter> registrationBean = new FilterRegistrationBean<SessionFilter>();
  SessionFilter customURLFilter = new SessionFilter();

  registrationBean.setFilter(customURLFilter);
  registrationBean.addUrlPatterns(
		  "/indexPage",
		  "/region",
		  "/utilisateur",
		  "/ajouterRegion",
		  "/ajouterTraitement",
		  "/modifier/*",
		  "/modifierTraitement",
		  "/signalement",
		  "/ajouterType",
		  "/ajouterTypeTraitement",
		  "/listeType",
		  "/affectation/*",
		  "/affectationTraitement"
		  );//mandalo filtre
  registrationBean.setOrder(2); //set precedence
  return registrationBean;
 }
}
