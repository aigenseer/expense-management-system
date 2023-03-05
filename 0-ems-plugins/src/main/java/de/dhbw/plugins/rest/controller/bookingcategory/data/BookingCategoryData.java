package de.dhbw.plugins.rest.controller.bookingcategory.data;

import de.dhbw.ems.adapter.mapper.data.bookingcategory.IBookingCategoryData;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BookingCategoryData implements IBookingCategoryData {

    @NotEmpty(message = "The title is required.")
    @Size(min = 2, max = 100, message = "The length of full title must be between 2 and 100 characters.")
    private String title;

}