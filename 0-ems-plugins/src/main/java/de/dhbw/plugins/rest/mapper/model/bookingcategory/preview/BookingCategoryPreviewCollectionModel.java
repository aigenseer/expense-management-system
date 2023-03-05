package de.dhbw.plugins.rest.mapper.model.bookingcategory.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingCategoryPreviewCollectionModel extends CollectionModel<BookingCategoryPreviewModel> {

    public BookingCategoryPreviewCollectionModel(Iterable<BookingCategoryPreviewModel> content){
        super(content);
    }

}
