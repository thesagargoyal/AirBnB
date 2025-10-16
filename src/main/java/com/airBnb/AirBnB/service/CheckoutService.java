package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
