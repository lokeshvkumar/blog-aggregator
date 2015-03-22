package us.jblog.aggregator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import us.jblog.aggregator.validator.UniqueUserNameValidator;

/**
 * This class added as part of step. Look @ the Size annotation source and copy all you need to this
 * annotation.
 * We applying this only on field.
 */
@Target({ FIELD })
@Retention(RUNTIME)
//@Documented//not needed
//need to provide validator class to the constraint. Look at spring documentation. section 'Configuring Custom Constraints' under http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#validation-beanvalidation-spring-constraints
//
@Constraint(validatedBy = { UniqueUserNameValidator.class })
public @interface UserNameConstraint {

	String message() default "{javax.validation.constraints.Size.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
