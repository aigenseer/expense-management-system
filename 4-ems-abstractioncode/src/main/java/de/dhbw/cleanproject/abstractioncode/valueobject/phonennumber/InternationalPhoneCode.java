package de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InternationalPhoneCode {
    DE("+49");
    private final String code;
}
