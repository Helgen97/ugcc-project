package com.project.ugcc.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testTransliterateStringFromCyrillicToLatinChars() {
        String expected = "Privit-svit";
        String actual = Utils.transliterateStringFromCyrillicToLatinChars("Привіт світ");

        assertEquals(expected, actual);
    }

    @Test
    void testTransliterateStringFromCyrillicToLatinCharsTakesEmptyString() {
        String expected = "";
        String actual = Utils.transliterateStringFromCyrillicToLatinChars("");

        assertEquals(expected, actual);
    }

    @Test
    void testTransliterateStringFromCyrillicToLatinCharsTakesNull() {
        String expected = "";
        String actual = Utils.transliterateStringFromCyrillicToLatinChars(null);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertDateToUkrainianDateString() {
        LocalDateTime date = LocalDateTime.of(2022, 1, 10, 10, 10);
        String expected = "10 січня 2022";
        String actual = Utils.convertDateToUkrainianDateString(date);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertDateToUkrainianDateStringTakesNull() {
        String expected = "1 січня 2022";
        String actual = Utils.convertDateToUkrainianDateString(null);

        assertEquals(expected, actual);
    }
}