package com.fornacif.lotocado.utils;

public final class Constants {
    public static final String EVENT_ENTITY = "Event";
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_ORGANIZER_NAME = "organizerName";
    public static final String EVENT_ORGANIZER_EMAIL = "organizerEmail";
    public static final String EVENT_DATE = "date";
    
    public static final String PARTICIPANT_ENTITY = "Participant";
    public static final String PARTICIPANT_ID = "id";
    public static final String PARTICIPANT_EMAIL = "email";
    public static final String PARTICIPANT_NAME = "name";
    public static final String PARTICIPANT_IS_RESULT_CONSULTED = "isResultConsulted";
    public static final String PARTICIPANT_TO_KEY = "toKey";
    public static final String PARTICIPANT_TO_NAME = "toName";
    public static final String PARTICIPANT_EXCLUSION_KEYS = "exclusionKeys";
    public static final String PARTICIPANT_EVENT_KEY = "eventKey";
    
    public static final int NOT_PERSISTED_ERROR_CODE = 521;
    public static final int NO_RESULT_ERROR_CODE = 522;
    public static final int SEND_MAIL_TO_ORGANIZER_ERROR_CODE = 523;
    public static final int SEND_MAIL_TO_PARTICIPANT_ERROR_CODE = 524;
    public static final int DATA_ERROR_CODE = 525;
}
