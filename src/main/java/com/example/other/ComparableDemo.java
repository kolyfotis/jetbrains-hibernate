package com.example.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComparableDemo {
    private static class Name implements Comparable<Name> {
        private final String firstName, lastName;

        public Name(String firstName, String lastName) {
            if (firstName == null || lastName == null)
                throw new NullPointerException();
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public int compareTo(Name o) {
            int lastCmp = lastName.compareTo(o.lastName);
            return (lastCmp !=0 ? lastCmp : firstName.compareTo(o.firstName));
        }

        @Override
        public String toString() {
            return "\nName{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Name fotis1 = new Name("Fotis", "Kolytoumpas");
        Name fotis2 = new Name("Fotis", "Kolytoumpas");
        Name someoneElse = new Name("Someone", "Else");

        List<Name> names = Arrays.asList(new Name[] {fotis1, fotis2, someoneElse});
        Collections.sort(names);

        System.out.println(names);

//        System.out.printf("Result is: %d.%n", fotis1.compareTo(fotis2));
    }
}
