package com.rafaelbenz.sgsc.sgscapi.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable {
    private static final long serialVersionUID= 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldMessage> errors) {
        this.errors = errors;
    }

    public void addError(FieldMessage fieldMessage){
        this.errors.add(fieldMessage);
    }

    public void addError(String fieldName, String message){
        this.errors.add(new FieldMessage(fieldName,message));
    }
}
