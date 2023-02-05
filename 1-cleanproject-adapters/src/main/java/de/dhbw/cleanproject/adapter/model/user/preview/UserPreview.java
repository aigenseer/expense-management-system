package de.dhbw.cleanproject.adapter.model.user.preview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Builder
@Getter
public class UserPreview extends RepresentationModel<UserPreview>{
    @JsonIgnore
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
}


