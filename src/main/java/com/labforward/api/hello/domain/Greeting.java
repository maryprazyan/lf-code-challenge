package com.labforward.api.hello.domain;

import com.labforward.api.core.validation.Entity;
import com.labforward.api.core.validation.EntityUpdateValidatorGroup;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Simple greeting message for dev purposes
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Greeting implements Entity {

	@NotEmpty(groups = {EntityUpdateValidatorGroup.class})
	private String id;

	@NotEmpty
	private String message;

	public Greeting(String message) {
		this.message = message;
	}
}
