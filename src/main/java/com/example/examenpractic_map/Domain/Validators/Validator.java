package com.example.examenpractic_map.Domain.Validators;

import com.example.examenpractic_map.Exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
