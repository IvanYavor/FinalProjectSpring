package com.company.validation;

import com.company.domain.EditUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class ScoresConstraintValidator implements ConstraintValidator<ScoresConstraint, EditUserForm> {
    @Override
    public void initialize(ScoresConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(EditUserForm editUserForm, ConstraintValidatorContext constraintValidatorContext) {


//        Set<String> classes = new HashSet<>();
//        classes.add("biology");
//        classes.add("english");
//        classes.add("math");
//        classes.add("chemistry");


//        for(String key : form.keySet()) {
//            if(classes.contains(key) && (parseInt(form.get(key)) < 0 || parseInt(form.get(key)) > 100)) {
//                return false;
//            }
//        }

        for(String key: editUserForm.getScores().keySet()) {
            if(parseInt(editUserForm.getScores().get(key)) < 0 || parseInt(editUserForm.getScores().get(key)) > 100) {
                return false;
            }
        }


        return true;
    }
}
