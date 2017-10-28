package main.entities;

import javax.persistence.*;

@Entity(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "z")
    private double z;

    public Point(){}

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Long id, double x, double y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getZ(){
        return z;
    }
    public Long getId(){
        return id;
    }
    @Override
    public String toString(){
        return "X:" + x + ", Y:" + y;
    }
}
