package de.dhbw.plugins.rest.mapper.model.booking.preview;

import org.springframework.hateoas.CollectionModel;

public class BookingPreviewCollectionModel extends CollectionModel<BookingPreviewModel> {

    public BookingPreviewCollectionModel(Iterable<BookingPreviewModel> content){
        super(content);
    }

}
