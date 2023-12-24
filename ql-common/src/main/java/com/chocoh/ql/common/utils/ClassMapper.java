package com.chocoh.ql.common.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author chocoh
 */
public abstract class ClassMapper {
    public static <T> List<Field> classToList(T t) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Class<?> thisClass = t.getClass(); !thisClass.equals(Object.class); thisClass = thisClass.getSuperclass()) {
            fields.addAll(Arrays.asList(thisClass.getDeclaredFields()));
        }
        return fields;
    }

    public static <T> Map<String, Object> classToMap(T t) throws IllegalAccessException {
        Class<?> aclass = t.getClass();
        HashMap<String, Object> map = new HashMap<>();
        Class<?> thisClass = aclass;
        while (!thisClass.equals(Object.class)) {
            Field[] fields = thisClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(t));
            }
            thisClass = thisClass.getSuperclass();
        }
        Class<?>[] interfaces = aclass.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            Field[] fields = interfaceClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(t));
            }
        }
        return map;
    }

    public static <T> void mapToClass(HashMap<String, ?> map, T t) throws IllegalAccessException {
        Class<?> thisClass = t.getClass();
        while (!thisClass.equals(Object.class)) {
            Field[] fields = thisClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    field.set(t, map.get(field.getName()));
                }
            }
            thisClass = thisClass.getSuperclass();
        }
    }
}
