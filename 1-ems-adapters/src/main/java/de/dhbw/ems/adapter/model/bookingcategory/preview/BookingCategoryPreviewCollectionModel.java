package de.dhbw.ems.adapter.model.bookingcategory.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingCategoryPreviewCollectionModel extends CollectionModel<BookingCategoryPreviewModel> {

    public BookingCategoryPreviewCollectionModel(Iterable<BookingCategoryPreviewModel> content){
        super(content);
    }

}
