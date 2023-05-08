package uz.test.abitur.dtos.auth;

import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record TokenRequest(@NonNull String phoneNumber, @NonNull String password) {
}
