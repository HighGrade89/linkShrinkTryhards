package com.example.linkshrink.constants;

/**
 * Содержит заданные сообщения об ошибках
 */
public class ErrorMessages {

    public static final String GENERAL_ERROR = "[E500] Непредвиденная ошибка";
    public static final String MAPPER_ERROR = "[E400] Некорректный запрос";
    public static final String URL_NOT_FOUND = "[E001] URL не найден";
    public static final String INBOUND_URL_INVALID = "[E002] Входящий URL некорректен";
    public static final String URL_HAS_EXPIRED = "[E003] Время действия URL истекло";

    private  ErrorMessages() {}
}

