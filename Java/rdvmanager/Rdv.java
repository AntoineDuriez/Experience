/*
 * Copyright (C) 2019 EIDD 2A SIE
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package rdvmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A <code>Rdv</code> object represents an appointment with a date, a
 * description and a span.
 *
 * @version 0.1
 * @author M. Sighireanu
 * @author YOU
 */
public class Rdv {

    // TODO: EXO 1.1
    private String description;
    private LocalDateTime start;
    private int spanMinutes;

    /**
     * Constructor of a rendezvous from description, date, time and span.
     *
     * TODO: EXO 2.1
     *
     * @param aDescription short text
     * @param aYear the year from from MIN_YEAR to MAX_YEAR
     * @param aMonth the month of year from 1 to 12
     * @param dayOfMonth the day-of-month from 1 to 31
     * @param aHour the hour-of-day from 0 to 23
     * @param aMinute the time of a day from 0 to 59
     * @param aSpan the duration in minutes
     */
    public Rdv(String aDescription,
            int aYear, int aMonth, int dayOfMonth,
            int aHour, int aMinute, int aSpan) {
        // TODO: EXO 1.1
        // TODO: EXO 2.2
        description = aDescription;
        start = LocalDateTime.of(aYear, aMonth, dayOfMonth, aHour, aMinute);
        spanMinutes = aSpan;
    }

    /**
     * Constructor of a rendezvous from description, date, time and span.
     *
     * @param aDescription short text
     * @param aDate the start date
     * @param aTime the start time
     * @param aSpan the duration in minutes
     */
    public Rdv(String aDescription,
            LocalDate aDate, LocalTime aTime, int aSpan) {
        // TODO: EXO 1.1
        // TODO: EXO 2.2
        this.description = aDescription;
        this.start = LocalDateTime.of(aDate, aTime);
        this.spanMinutes = aSpan;
    }

    /**
     * Get description of the appointment.
     *
     * @return the text describing the rendezvous
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get starting time
     *
     * @return the text describing the rendezvous
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Get duration
     *
     * @return the span
     */
    public int getSpan() {
        return this.spanMinutes;
    }

    /**
     * Print to string
     *
     * @return the string of this rendezvous
     */
    public String toString() {
        return this.start.toLocalDate() + " "
                + this.start.toLocalTime() + " "
                + "(" + this.spanMinutes + "min)"
                + ": " + this.description;
    }

    // TODO: EXO 1.5
    /**
     * Print to CSV line
     *
     * @return the string of this rendezvous
     */
    public String toCSV() {
        return this.start.toLocalDate() + ";"
                + this.start.toLocalTime() + ";"
                + this.spanMinutes + ";"
                + this.description;
    }
}
