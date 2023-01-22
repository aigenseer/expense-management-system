package de.dhbw.cleanproject.adapter.user.userpreview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class UserPreview extends RepresentationModel<UserPreview>{
    private String name;
    private String email;
    private String phoneNumber;
}
