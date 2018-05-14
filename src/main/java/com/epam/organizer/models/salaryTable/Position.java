package com.epam.organizer.models.salaryTable;

public class Position {
    private String level;
    private int salary;
    private int overhead;

    public Position(String level, int salary, int overhead) {
        this.level = level;
        this.salary = salary;
        this.overhead = overhead;
    }

    public String getLevel() {
        return level;
    }

    public int getSalary() {
        return salary;
    }

    public int getOverhead() {
        return overhead;
    }



    @Override
    public String toString() {
        return "Position{" +
                "level='" + level + '\'' +
                ", salary='" + salary + '\'' +
                ", overhead='" + overhead + '\'';
    }
}
