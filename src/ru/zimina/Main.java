package ru.zimina;

// Задача 1. Форт Нокс

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) {

        Vault federalVault = new Vault();
//        Field[] fields = federalVault.getClass().getDeclaredFields();
//        //System.out.println(Arrays.toString(fields));
//        //Arrays.stream(fields).forEach(System.out::println);
//
//        printDollars(federalVault);
//
//        // Получили доступ к приватному методу класса
//        try {
//            Method method = federalVault.getClass().getDeclaredMethod("setDollars", int.class);
//            method.setAccessible(true);
//            method.invoke(federalVault, 20);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        //Arrays.stream(methods).forEach(System.out::println);
//        printDollars(federalVault);

        try {
            Constructor c = federalVault.getClass().getDeclaredConstructor(int.class, int.class, double.class, String.class);
            c.setAccessible(true);

            printVaultFields(federalVault, "Поля первого объекта до изменений:");

            int dollars = (int) getField(federalVault, "dollars").get(federalVault);
            int euros = (int) getField(federalVault, "euros").get(federalVault);
            double tonsOfGold = (double) getField(federalVault, "tonsOfGold").get(federalVault);
            String pentagonNukesCodes = (String) getField(federalVault, "pentagonNukesCodes").get(federalVault);

            getPrivateMethod(federalVault, "setDollars", int.class).invoke(federalVault, 0);
            getPrivateMethod(federalVault, "setEuros", int.class).invoke(federalVault, 0);
            getPrivateMethod(federalVault, "setTonsOfGold", double.class).invoke(federalVault, 0.0);
            getPrivateMethod(federalVault, "setPentagonNukesCodes", String.class ).invoke(federalVault, "null");

            printVaultFields(federalVault, "Поля первого объекта после изменений:");

            Vault myVault = (Vault) c.newInstance(dollars, euros,  tonsOfGold, pentagonNukesCodes);
            printVaultFields(myVault, "Поля новго обьекта:");

        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void printVaultFields(Vault vault, String message) {
        System.out.println(message);

        try {
            int dollars = (int) getField(vault, "dollars").get(vault);
            int euros = (int) getField(vault, "euros").get(vault);
            double tonsOfGold = (double) getField(vault, "tonsOfGold").get(vault);
            String pentagonNukesCodes = (String) getField(vault, "pentagonNukesCodes").get(vault);

            System.out.println(format("dollars= %d, euros= %d, tonsOfGold= %.1f, pentagonNukesCodes= %s\r\n" +
                            "=================================================================================",
                    dollars,
                    euros,
                    tonsOfGold,
                    pentagonNukesCodes));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();

        }

    }

//    private static void printDollars(Vault federalVault) {
//        // Получили доступ к приватной переменной класса
//        try {
//            Field dollarsField = federalVault.getClass().getDeclaredField("dollars");
//            dollarsField.setAccessible(true); // компилятор игнорирует модификаторы доступа
//            int secretDollars = (int)dollarsField.get(federalVault);
//            System.out.println(secretDollars);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    private static Field getField (Vault federalVault, String fieldName) throws NoSuchFieldException {
        // Получаем доступ к приватной переменной класса
        Field field = federalVault.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // компилятор игнорирует модификаторы доступа

        return field;
    }

    private static Method getPrivateMethod(Vault federalVault, String methodName, Class valueClass) throws NoSuchMethodException {
        // Получили доступ к приватному методу класса
        Method method = federalVault.getClass().getDeclaredMethod(methodName, valueClass);
        method.setAccessible(true);
        return method;
    }


}
