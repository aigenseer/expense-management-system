package de.dhbw.plugins.rest.mapper.model.user.preview;

import org.springframework.hateoas.CollectionModel;

public class UserPreviewCollectionModel extends CollectionModel<UserPreview> {

    public UserPreviewCollectionModel(Iterable<UserPreview> content){
        super(content);
    }

}
