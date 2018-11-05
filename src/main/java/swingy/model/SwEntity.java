package swingy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public abstract class SwEntity {

    @NotNull
    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "^([a-zA-Z0-9 _])*$", message = "Should contain only letters, numbers, space and _")
    @Getter protected String name;

    @NotNull
    @NotBlank
    @Getter protected String type;

    /*
     * Constructor
     */
    public SwEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.type + ")";
    }
}
