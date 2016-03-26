package com.mkyong.form.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mkyong.form.model.MyTask;
import com.mkyong.form.model.User;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class TaskFormValidator implements Validator {

    private static Logger m_logger = LoggerFactory.getLogger(TaskFormValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
	    
	    m_logger.debug("TaskFormValidator.supports()");
	    
		return MyTask.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

        m_logger.debug("TaskFormValidator.validate() " + target);
	    
//	    MyTask myTask = (MyTask) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "NotEmpty.userForm.email");
		
	}

}