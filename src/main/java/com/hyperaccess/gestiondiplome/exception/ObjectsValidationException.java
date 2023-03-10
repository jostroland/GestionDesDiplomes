package com.hyperaccess.gestiondiplome.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ObjectsValidationException extends RuntimeException{

    @Getter
    private final Set<String> violations;

    @Getter
    private final String violationSource;
}
