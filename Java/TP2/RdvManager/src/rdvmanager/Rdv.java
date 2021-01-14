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
import java.time.Month;
import java.util.Objects;

/**
 * A <code>Rdv</code> object represents an appointment with a date, a
 * description and a span.
 *
 * @version 0.1
 * @author M. Sighireanu
 * @author YOU
 */
public class Rdv {

    // TODO: EXO 1.2
    private String description = "unknown"; //valeurs par défaut
    private LocalDateTime start = LocalDateTime.now();
    private int spanMinutes = 30;
    //preconditions rules
    final int MIN_YEAR = 2000;
    final int MAX_YEAR = 3000;
    /**
     *Constructor of a rendezVous from description.
     * @param aDescription short text
     */
    public Rdv(String aDescription){    //constructeur pour description appelé dans les autres
        this.description = Objects.requireNonNullElse(aDescription, this.description);
    }
    /**
     * Constructor of a rendezvous from description, date, time and span.
     *
     * DONE: EXO 2.1
     *
     * @param aDescription short text
     * @param aYear the year 
     * @param aMonth the month of year
     * @param dayOfMonth the day-of-month
     * @param aHour the hour-of-day
     * @param aMinute the time of a day
     * @param aSpan the duration in minutes
     * @precondition    MIN_YEAR <= aYear && aYear <= MAX_YEAR;
     * @precondition    1 <= aMonth && aMonth <= 12;
     * @precondition    1 <= dayOfMonth && dayOfMonth <= 31;
     * @precondition    0 <= aHour && aHour <= 23;
     * @precondition    0 <= aMinute && aMinute <= 59;
     * @precondition    0 < aSpan;
     */
    public Rdv(String aDescription,
            int aYear, int aMonth, int dayOfMonth,
            int aHour, int aMinute, int aSpan) {
        // TODO: EXO 1.1
        // TODO: EXO 2.2
        this(aDescription);// appel au constructeur plus haut
        //assertions tests
        assert  MIN_YEAR <= aYear && aYear <= MAX_YEAR : "Not a year !";
        assert  1 <= aMonth && aMonth <= 12 : "Not a month !";
        assert  1 <= dayOfMonth && dayOfMonth <= 31 : "Not a day !";
        assert  0 <= aHour && aHour <= 23 : "Not an hour !";
        assert  0 <= aMinute && aMinute <= 59 : "Not a minute !";
        assert  0 < aSpan : "Not a span !";
        this.start = LocalDateTime.of(aYear, aMonth, dayOfMonth, aHour, aMinute);
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
        // DONE: EXO 1.2
        this(aDescription, aDate.getYear(), aDate.getMonthValue(), aDate.getDayOfMonth(), aTime.getHour(), aTime.getMinute(), aSpan);
        /*si la date ou le temps est null, prendre les valeurs par défaut*/
        LocalDate d = this.start.toLocalDate(); //valeur par défaut
        d = Objects.requireNonNullElse(aDate, this.start.toLocalDate());
        LocalTime t = this.start.toLocalTime();
        t = Objects.requireNonNullElse(aTime, this.start.toLocalTime());
        this.start = LocalDateTime.of(aDate,aTime);
    // TODO: EXO 2.2
    }

/**
 *Redéfinition de la méthode equals, adapté à la classe Rdv
 * @param obj   l'objet à comparer
 * @return      retourne vrai si les 2 objets sont identiques, false sinon
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        //si les classes ne sont pas identiques
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rdv other = (Rdv) obj;
        if (this.spanMinutes != other.spanMinutes) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        return true;
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

