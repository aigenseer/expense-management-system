package de.dhbw.cleanproject.adapter.user.userpreview;

import org.springframework.hateoas.CollectionModel;

public class UserPreviewCollectionModel extends CollectionModel<UserPreview> {

    public UserPreviewCollectionModel(Iterable<UserPreview> content){
        super(content);
    }

}
