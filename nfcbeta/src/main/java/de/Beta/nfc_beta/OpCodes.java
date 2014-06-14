package de.Beta.nfc_beta;

/**
 * Created by Kern on 10.06.2014.
 */
/*public interface Operations {
    public static final String OPC_SILENT = "exec_mutephone";
    public static final String OPC_CONTACT = "exec_addcontact";
    public static final String OPC_SOUND = "lnk_sound";
    public static final String OPC_IMAGE = "raw_image";
    public static final String OPC_ROUTE = "coord_maps"; // GPS Koordinaten
}*/

public interface OpCodes {
    public static final int OPC_SILENT = 1001;
    public static final int OPC_CONTACT = 1002;
    public static final int OPC_SOUND = 1003;
    public static final int OPC_IMAGE = 1004;
    public static final int OPC_ROUTE = 1005; // GPS Koordinaten
}