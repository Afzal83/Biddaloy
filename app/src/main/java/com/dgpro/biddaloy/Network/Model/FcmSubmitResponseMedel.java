package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 2/12/2018.
 */

public class FcmSubmitResponseMedel {
    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;
}
