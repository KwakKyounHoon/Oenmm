package com.onemeter.omm.onemm.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaces  {
    private List<DonatingPlace> donatingPlaceList = new ArrayList<>();
    private DonatingPlace headerDonatingPlace;

    public List<DonatingPlace> getDonatingPlaceList() {
        return donatingPlaceList;
    }

    public void setDonatingPlaceList(List<DonatingPlace> donatingPlaceList) {
        this.donatingPlaceList = donatingPlaceList;
    }

    public DonatingPlace getHeaderDonatingPlace() {
        return headerDonatingPlace;
    }

    public void setHeaderDonatingPlace(DonatingPlace headerDonatingPlace) {
        this.headerDonatingPlace = headerDonatingPlace;
    }
}
