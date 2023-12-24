package com.chocoh.ql.common.utils;

import java.util.HashMap;

/**
 * @author chocoh
 */
public class MapUtil<K, V> {
    private HashMap<K, V> map = new HashMap<>();

    public MapUtil<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public V get(K key) {
        return map.get(key);
    }

    public MapUtil<K, V> remove(K key) {
        map.remove(key);
        return this;
    }

    public Boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public static <K, V> HashMap<K, V> of(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static HashMap<String, Object> ofObject(String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public HashMap<K, V> getMap() {
        return map;
    }

    public void setMap(HashMap<K, V> map) {
        this.map = map;
    }
}
