package de.dhbw.cleanproject.adapter.bookingcategory.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingCategoryPreviewCollectionModel extends CollectionModel<BookingCategoryPreviewModel> {

    public BookingCategoryPreviewCollectionModel(Iterable<BookingCategoryPreviewModel> content){
        super(content);
    }

}
