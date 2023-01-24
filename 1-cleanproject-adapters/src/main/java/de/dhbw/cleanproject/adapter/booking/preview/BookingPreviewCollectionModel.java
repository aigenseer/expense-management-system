package de.dhbw.cleanproject.adapter.booking.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingPreviewCollectionModel extends CollectionModel<BookingPreviewModel> {

    public BookingPreviewCollectionModel(Iterable<BookingPreviewModel> content){
        super(content);
    }

}
