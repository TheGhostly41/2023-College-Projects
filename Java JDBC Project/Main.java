/*
Jose Urbina, Kelsey Acosta, Eric Wade
Nov 30, 2023
Java JDBC Project
*/

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Login
        String username = "root";
        String URL = "jdbc:mysql://localhost:3307/contactbook";
        String password = "usbw";

        Scanner input = new Scanner(System.in);

        // User input
        System.out.println("Hello user, \nWhat action would you like to do?" +
                "\n  A. Add Rows" + "\n  B. Find someone's info" + "\n  C. Change info" + "\n  D. Delete Data");

        System.out.println("\nEnter choice: ");
        char c = input.next().charAt(0);

        // Switch case which calls other methods
        switch (c) {
            case 'A':
            case 'a':
                New_Contact(URL, username, password);
                break;

            case 'B':
            case 'b':
                lookup_Contact(URL, username, password);
                break;

            case 'C':
            case 'c':
                Update_Contact(URL, username, password);
                break;

            case 'D':
            case 'd':
                Delete_Contact(URL, username, password);
                break;

            default:
                System.out.println("You chose nothing, Have a new day!");
                break;
        }

    }

    // Method to Add new rows
    public static void New_Contact(String URL, String username, String password){

        Scanner input = new Scanner(System.in);
        String Fname, Nickname, Lname, Email, PhoneNum, Company, Bday;

        int amount_of_rows, Changed = 0;

        System.out.println("\n\n---------ADD MODE---------");

        System.out.print("How many rows do you want to input: ");
        amount_of_rows = input.nextInt();

        try{

            // Creating Connection
            Connection con = DriverManager.getConnection(URL, username, password);

            // Making the statement
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // For loop for adding contact info
            for(int i = 0; i < amount_of_rows; i++){

                System.out.println("-----ROW " + (i+1) + "-----");

                System.out.print("Insert Contact's First name: ");
                Fname = input.next();

                System.out.print("Insert Contact's Last name: ");
                Lname = input.next();

                System.out.print("Insert Contact's Nickname: ");
                Nickname = input.next();

                System.out.print("Insert Contact's Phone Number: ");
                PhoneNum = input.next();

                System.out.print("Insert Contact's Email: ");
                Email = input.next();

                System.out.print("Insert Contact's Company: ");
                Company = input.next();

                System.out.print("Insert Contact's Birthday: ");
                Bday = input.next();

                // Sql code
                String sql = "insert into contacts (FName, Nicname, LName, PhoneNo, Email, Company, Birthday)" +
                        "values ('" + Fname + "', '" + Nickname + "', '" + Lname + "', '" + PhoneNum + "', '" + Email +
                        "', '" + Company + "', '" + Bday + "')";

                // update table
                Changed = st.executeUpdate(sql);
            }

            // Output
            if (Changed > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Failed to insert data.");
            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
        }
    }

    // Method to Delete rows
    public static void Delete_Contact(String URL, String username, String password) {
        Scanner input = new Scanner(System.in);

        String del;
        int Deleted = 0;

        System.out.println("\n\n---------DELETE MODE---------");

        System.out.print("How many contacts do you want to delete: ");
        int amount_of_rows = input.nextInt();

        try {
            // Creating Connection
            Connection con = DriverManager.getConnection(URL, username, password);

            // Making the statement
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // For loop for deleting multiple rows
            for (int i = 0; i < amount_of_rows; i++) {
                System.out.print("Enter phone number of the contact you want to delete: ");
                del = input.next();
                System.out.println();

                // Execute statement
                Deleted = st.executeUpdate("DELETE FROM contacts WHERE PhoneNo = '" + del + "'");
            }

            if (Deleted > 0) {
                System.out.println("Rows Deleted");
            } else {
                System.out.println("No rows deleted. Invalid Phone Number");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method to find person's info
    public static void lookup_Contact(String URL, String username, String password){

        Scanner input = new Scanner(System.in);

        String name;

        System.out.println("\n\n---------LOOKUP MODE---------");

        try {
            // Creating Connection
            Connection con = DriverManager.getConnection(URL, username, password);

            // Making the statement
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Finding contact info
            System.out.print("Enter the name of the person: ");
            name = input.next();
            System.out.println();

            String sql = "Select * from contacts where FName = '" + name + "'";

            ResultSet rs = st.executeQuery(sql);

            // Output
            while(rs.next()){
                System.out.println(rs.getInt(1) + "   " + rs.getString(2) +
                        "   " + rs.getString(3) + "   " + rs.getString(4) +
                        "   " + rs.getString(5) + "   " + rs.getString(6) +
                        "   " + rs.getString(7) + "   " + rs.getString(8));
            }

            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }

    }

    // Method to update info
    public static void Update_Contact(String URL, String username, String password) {
        Scanner input = new Scanner(System.in);

        String old, New, tab, lookup;
        int Update = 0;

        System.out.println("\n\n---------UPDATE MODE---------");

        System.out.print("How many contacts do you want to update: ");
        int amount_of_rows = input.nextInt();

        try {
            // Creating Connection
            Connection con = DriverManager.getConnection(URL, username, password);

            // Making the statement
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // For loop for updating multiple rows
            for (int i = 0; i < amount_of_rows; i++) {

                System.out.println("-----UPDATE " + (i+1) + "-----");

                System.out.println("\nColumns: " + "\nFName" + "\nLName" +
                        "\nNicname" + "\nPhoneNo" + "\nEmail" + "\nCompany" + "\nBirthday");

                System.out.print("\nEnter column: ");
                tab = input.next();

                System.out.println("Enter the Old value you want to change: ");
                old = input.next();

                System.out.println("Enter the New value you want to change: ");
                New = input.next();

                String lookup_sql = "select * from contacts where " + tab + " = '" + old + "'";
                ResultSet rs = st.executeQuery(lookup_sql);

                // getting the id for update "WHERE"
                if (rs.next()) {
                    lookup = "" + rs.getInt(1);

                    // Execute statement
                    String update_sql = "update contacts set " + tab + " = '" + New + "'" +
                            " where " + tab + " = '" + old + "'";
                    Update = st.executeUpdate(update_sql);

                } else {
                    System.out.println("No matching row found for update.");
                }
            }

            // Output
            if (Update > 0) {
                System.out.println("Rows Updated");
            } else {
                System.out.println("Error, row did not update.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}