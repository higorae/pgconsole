package com.github.luksrn.postgresql.utils

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
class FieldErrorResource {
	String resource;
	String field;
	String code;
	String message;
}
