package com.example.parkingpal;

import java.util.Date;

/**
 * Parking Permit Class
 *
 * Description: Linked to a user's account in order to register
 * the type of permit they have and determine where they may be
 * allowed to park.
 *
 * @author Daniel Duany
 */
public class ParkingPermit {
    // Permit Type
    private PermitType type;
    // Expiration Date
    private Date expiration;
    // Keeping type of permits in enum class
    public enum PermitType {
        RESIDENT(0), COMMUTER(1), EVENING_COMMUTER(2), GRADUATE(3), CARPOOL(4), RESTRICTED_USE(5), LONG_TERM_STORAGE(6);

        private final int ptype;

        PermitType(int type) {
             this.ptype = type;
        }
    }

    /**
     * getType()
     * Accessor method for permit type
     * @return int representing permit type
     */
     public int getType() {
         switch(type) {
             case RESIDENT:
                 return 0;
             case COMMUTER:
                 return 1;
             case EVENING_COMMUTER:
                 return 2;
             case GRADUATE:
                 return 3;
             case CARPOOL:
                 return 4;
             case RESTRICTED_USE:
                 return 5;
             case LONG_TERM_STORAGE:
                 return 6;
         }
         // If the type is not found
         return -1;
     }

    /**
     * ParkingPermit(int)
     *
     * Parking Permit constructor
     *
     * Will determine the permit type depending on information
     * given by the user. Each of these number should be associated
     * with a number returned from the user clicking a radio button
     * and passed into this constructor to log the permit type.
     * @param permitType
     */
    public ParkingPermit(int permitType) {
        switch(permitType){
            case 0:
                type = PermitType.RESIDENT;
                break;
            case 1:
                type = PermitType.COMMUTER;
                break;
            case 2:
                type = PermitType.EVENING_COMMUTER;
                break;
            case 3:
                type = PermitType.GRADUATE;
                break;
            case 4:
                type = PermitType.CARPOOL;
                break;
            case 5:
                type = PermitType.RESTRICTED_USE;
                break;
            case 6:
                type = PermitType.LONG_TERM_STORAGE;

        }
    }
}
