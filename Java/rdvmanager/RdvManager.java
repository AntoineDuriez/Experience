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

import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Manager of appointments
 *
 * @version 0.1
 * @author M. Sighireanu
 * @author YOU
 */
public class RdvManager {

    // TODO: EXO 1.10
    private String userName = "anonymous";
    private RdvList rdvList = new RdvList();
    private Scanner input = new Scanner(System.in);
    private PrintStream output = System.out;
    // TODO: EXO 4.1

    /**
     * Initializes the manager for the given user's name.
     *
     * @param aName user name for greetings
     */
    public RdvManager(String aName) {
        this.userName = aName;
        this.rdvList = new RdvList();
    }

    /**
     * Get the user's name.
     *
     * @return the user name
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Get the name of the file saving the user appointments.
     *
     * @return the filename
     */
    private String getFileName(String version) {
        String fileSep = System.getProperty("file.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(".myrdv");
        sb.append(fileSep);
        sb.append(this.userName.trim().replaceAll(" ", "-"));
        if (!version.isEmpty()) {
            sb.append(version.trim().replaceAll(" ", "-"));
        }
        sb.append(".csv");
        return sb.toString();
    }

    /**
     * Print identity into a string.
     *
     * @return a string with the identity
     */
    public String toString() {
        return "Appointment Manager of " + this.userName;
    }

    private void toUser(String msg) {
        output.print("# " + msg);
    }

    private void prompt(String msg) {
        output.print("> ");
        output.flush();
    }

    /**
     * Print greetings to user.
     */
    private void greetings() {
        this.toUser(toString() + ": Hello! I'm ready.\n");
        this.toUser("Current time: "
                + LocalDate.now()
                + " "
                + LocalTime.now()
                + "\n");
    }

    /**
     * Execution loop for manager.
     */
    public void printMenu() {
        // be polite
        this.greetings();
        // init the appointment list for the user
        try {
            this.readList();
        } catch (IOException e) {
            // file does not exist, print a message
            this.toUser("Warning: No appointment list found!\n");
            this.toUser("Create a new list.\n");
        }
        // the main loop
        boolean quit = false;
        while (!quit) {
            char choice = this.readChoice();
            switch (choice) {
                case 'p':
                    this.doPrint();
                    break;
                case 'a':
                    this.doAdd();
                    break;
                case 'm':
                    this.doModify();
                    break;
                case 'c':
                    this.doClear();
                    break;
                case 's':
                    this.doSave();
                    break;
                case 'q':
                    quit = this.doQuit();
                    break;
                default: {
                    this.toUser("Sorry, "
                            + "'" + choice + "'"
                            + "is not an option.\n");
                }
            }
        }

    }

    private char readChoice() {
        String choice = "";
        this.toUser("Please enter command ["
                + "p(rint),"
                + "a(dd),"
                + "m(odify),"
                + "c(lear),"
                + "s(ave),"
                + "q(uit)"
                + "]\n");
        this.prompt("");
        while (choice.isEmpty() && this.input.hasNext()) {
            choice = this.input.next().trim();
        }
        return choice.charAt(0);
    }

    private LocalDate readDate() {
        LocalDate d = null;
        this.toUser("Enter date (YYYY-MM-DD)\n");
        this.prompt("");
        do {
            String dText = "";
            while (dText.isEmpty() && this.input.hasNext()) {
                dText = this.input.next();
            }
            String[] dFields = dText.trim().split("-");
            if (dFields.length == 3) {
                try {
                    d = LocalDate.of(Integer.parseInt(dFields[0]),
                            Integer.parseInt(dFields[1]),
                            Integer.parseInt(dFields[2]));
                } catch (java.time.DateTimeException e) {
                    this.toUser("Not a date, enter '.' for today.\n");
                }
            } else if (dText.trim().equals(".")) {
                // take today
                d = LocalDate.now();
            } else {
                this.toUser("Format problem, enter '.' for today.\n");
            }
        } while (d == null);
        return d;
    }

    private LocalTime readTime() {
        LocalTime t = null;
        this.toUser("Enter start time (HH:MM)\n"); // read a start hour
        this.prompt("");
        do {
            String tText = "";
            while (tText.isEmpty() && this.input.hasNext()) {
                tText = this.input.next();
            }
            if (tText.equals(".")) {
                return LocalTime.now();
            }
            String[] tFields = tText.trim().split(":");
            int h = 0;
            int m = 0;
            if (tFields.length >= 1) {
                try {
                    h = Integer.parseInt(tFields[0]);
                } catch (NumberFormatException e) {
                    this.toUser("Error, take default hour 0\n");
                }
            }
            if (tFields.length >= 2) {
                try {
                    m = Integer.parseInt(tFields[1]);
                } catch (NumberFormatException e) {
                    this.toUser("Error, default minutes 0\n");
                }
            }
            try {
                t = LocalTime.of(h, m);
            } catch (java.time.DateTimeException e) {
                this.toUser("Not a time, enter '.' for now.\n");
            }
        } while (t == null);
        return t;
    }

    private int readSpan() {
        int span = 0;
        this.toUser("Enter span (minutes):\n"); // read a span
        this.prompt("");
        while (span == 0 && this.input.hasNext()) {
            String sText = this.input.next().trim();
            if (sText.equals(".")) {
                span = 30;
            } else {
                try {
                    span = Integer.parseUnsignedInt(sText);
                } catch (NumberFormatException e) {
                    this.toUser("Error, default 0\n");
                }
                if (span < 0 || span > 12 * 60) { // TODO: EXO 1.10
                    this.toUser("Not a valid span, enter '.' for 30 min.");
                    span = 0;
                }
            }
        }
        return span;
    }

    private String readDescription() {
        this.toUser("Enter description (one line):\n"); // read a description
        this.prompt("");
        String descr = "";
        while (descr.isEmpty() && this.input.hasNext()) {
            descr = this.input.nextLine().trim();
        }
        return descr;
    }

    /**
     * Print a (list of) appointment(s).
     */
    public void doPrint() {
        this.toUser("Print the list of rendezvous\n");
        // Read the starting time
        LocalDate d = this.readDate();
        LocalTime t = this.readTime();
        this.toUser("List of rendezvous for " + d + " after " + t + ":\n");
        // TODO: EXO 1.7
        this.rdvList.printFrom(this.output, LocalDateTime.of(d, t));
    }

    /**
     * Add an appointment.
     */
    public void doAdd() {
        this.toUser("Add a rendezvous\n");
        // Read the starting time       
        LocalDate d = this.readDate();
        LocalTime t = this.readTime();
        int span = this.readSpan();
        String descr = this.readDescription();
        Rdv rdv = new Rdv(descr, d, t, span);
        if (this.rdvList.add(new Rdv(descr, d, t, span))) {
            this.toUser("Rendezvous '"
                    + rdv
                    + "' successfully added.\n");
        } else {
            this.toUser("Rendezvous not added, check quota.\n");
        }
    }

    /**
     * Modify an appointment.
     */
    public void doModify() { // TODO
        this.toUser("Modify a rendezvous\n");
        this.toUser("Comming soon ...\n");
    }

    /**
     * Remove a (list of) appointment(s).
     */
    public void doClear() { // TODO: EXO 1.8, 1.9
        this.toUser("Clear a rendezvous list\n");
        this.toUser("Comming soon ...\n");
    }

    /**
     * Save the list of appointments in a file
     *
     * The filename is in <code>$HOME/.myrdv/username.csv</code> if the
     * directory exists.
     */
    public void doSave() {
        this.toUser("Save the rendezvous list ...\n");
        String fname = this.getFileName("");
        String ofname = this.getFileName("-old");
        Path p2file = Paths.get(System.getProperty("user.home"),
                fname);
        Path p2ofile = Paths.get(System.getProperty("user.home"),
                ofname);
        boolean fileExists = Files.exists(p2file,
                java.nio.file.LinkOption.NOFOLLOW_LINKS);
        if (fileExists) {
            // try to move
            try {
                Files.move(p2file, p2ofile,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                fileExists = false;
            } catch (IOException e) {
                this.toUser("Error while moving old list to file.\n");
            }
        }
        if (fileExists) {
            // can not be moved, try to remove
            try {
                Files.deleteIfExists(p2file);
                fileExists = false;
            } catch (IOException e) {
                this.toUser("Error while removing old file.\n");
            }
        }
        if (fileExists) {
            return;
        }

        PrintStream outFile;
        try {
            OutputStream out = Files.newOutputStream(
                    p2file,
                    java.nio.file.StandardOpenOption.CREATE_NEW);
            outFile = new PrintStream(out);
        } catch (IOException e) {
            this.toUser("Error while opening file.\n");
            return;
        }
        // TODO: EXO 2.5
        // outFile is not null
        this.rdvList.saveCSV(outFile);
        outFile.close();
    }

    /**
     * Create list of appointments from the user's file.
     *
     * @throws java.io.FileNotFoundException from Scanner
     */
    private void readList() throws IOException {
        String fname = this.getFileName(""); // TODO: EXO 1.10
        Path p2file = Paths.get(System.getProperty("user.home"), fname);
        InputStream inFile = Files.newInputStream(p2file);
        Scanner sc = new Scanner(inFile); // TODO: EXO 2.6 try-with-ressources
        sc.useDelimiter("");
        // read until quota is reached
        boolean isInQuota = true;
        int lines = 0;
        while (isInQuota && sc.hasNextLine()) {
            String csvLine = sc.nextLine();
            if (csvLine.startsWith(";")) {
                // read quota line of the form ";max-quota"
                try {
                    int newQuota = Integer.parseUnsignedInt(csvLine.substring(1).trim());
                    isInQuota = this.rdvList.setQuota(newQuota);
                } catch (NumberFormatException e) {
                    this.toUser("Quota ignored!");
                }
            } else {
                // normal line of the form "date;time;span;description
                String[] csv = csvLine.split(";");
                if (csv.length >= 4) {
                    LocalDate dateStart = LocalDate.parse(csv[0]);
                    LocalTime timeStart = LocalTime.parse(csv[1]);
                    Integer span = Integer.parseUnsignedInt(csv[2]);
                    isInQuota = this.rdvList.add(
                            new Rdv(csv[3], dateStart, timeStart, span));
                    lines++;
                }
                // TODO: EXO 4.2 else log the event  
            }
        }
        sc.close();
        this.toUser("" + lines + " lines read.\n");
    }

    /**
     * Quit the manager
     *
     * @return true if exit is confirmed
     */
    public boolean doQuit() {
        this.toUser("Please confirm (y/N)\n");
        this.prompt("");
        String response = "";
        while (response.isEmpty() && this.input.hasNext()) {
            response = this.input.nextLine().trim();
        }
        if (response.toLowerCase().startsWith("y")) {
            this.doSave();
            this.toUser("Bye!\n");
            return true;
        }
        return false;
    }

    /**
     * Main for manager
     *
     * @param args contains only the user name
     */
    public static void main(String[] args) {
        String user = "empty";
        if (args.length == 0) {
            System.out.println("Please provide a user name: ");
            Scanner sc = new Scanner(System.in);
            user = sc.nextLine();
        } else {
            user = args[0];
        }
        RdvManager manager = new RdvManager(user);
        manager.printMenu();
    }

}
