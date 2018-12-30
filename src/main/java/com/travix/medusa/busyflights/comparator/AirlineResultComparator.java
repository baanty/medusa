package com.travix.medusa.busyflights.comparator;

import java.util.Comparator;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

public class AirlineResultComparator implements Comparator<BusyFlightsResponse> {

	@Override
	public int compare(BusyFlightsResponse firstResponse, BusyFlightsResponse secondResponse) {
		return (int) (firstResponse.getFare() - secondResponse.getFare());
		}
	
}