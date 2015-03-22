package us.jblog.aggregator.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import us.jblog.aggregator.annotation.UserNameConstraint;
import us.jblog.aggregator.repository.UserRepository;


public class UniqueUserNameValidator implements ConstraintValidator<UserNameConstraint, String>{

	/*
	 * when the application is started, userRepository.save(userAdmin); method under InitDBService.init
	 * returns null because if this constraint. So have a check in isValid method for null respository.
	 */
	@Autowired
	UserRepository repository;
	
	@Override
	public void initialize(UserNameConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		if(repository == null) return true;
		//check if user is present in the database and return result.
		return repository.findByName(userName) == null;
	}

		
}
