/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

public class RegularPlayer extends Player{
    @Override
    public double calculateSalary() {
        return getBaseSalary();
    }
}
