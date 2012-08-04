package com.memmee.domain.password.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Password implements Serializable {

    private Long id;

    @NotNull
    private String value;

    private boolean temp = false;

    public Password() {
        super();
    }

    public Password(String value) {
        super();

        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }
}
