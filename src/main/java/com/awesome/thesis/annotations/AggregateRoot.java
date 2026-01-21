package com.awesome.thesis.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation für AggregatRoot. Diese Klasse ist mit {@link AggregateEntity} annotiert.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AggregateEntity
public @interface AggregateRoot {
}