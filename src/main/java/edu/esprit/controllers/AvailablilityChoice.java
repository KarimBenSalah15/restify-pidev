package edu.esprit.controllers;

import javafx.util.StringConverter;

public class AvailablilityChoice extends StringConverter<Boolean> {

    @Override
    public String toString(Boolean object) {
        if (object != null) {
            return object ? "Disponible" : "Non Disponible";
        }
        return null;
    }

    @Override
    public Boolean fromString(String string) {
        return "Disponible".equals(string);
    }
}

