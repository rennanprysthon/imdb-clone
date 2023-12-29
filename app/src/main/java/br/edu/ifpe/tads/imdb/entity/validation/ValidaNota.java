package br.edu.ifpe.tads.imdb.entity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target( {ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatorNota.class)
@Documented
public @interface ValidaNota {
    String message() default "Insira uma nota valida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
