package de.dhbw.ems.adapter.model.booking.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingPreviewCollectionModel extends CollectionModel<BookingPreviewModel> {

    public BookingPreviewCollectionModel(Iterable<BookingPreviewModel> content){
        super(content);
    }

}
