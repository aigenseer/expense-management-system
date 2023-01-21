package de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InternationalPhoneCode {
    DE("+49");

    private final String code;

}
