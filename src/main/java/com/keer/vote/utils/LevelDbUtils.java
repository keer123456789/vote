package com.keer.vote.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LevelDbUtils {
    private static DB db = null;
    private static String dbFolder = "leveldb/db/vote.db";
    private static String charset = "utf-8";

    static {
        initLevelDB();
    }

    /**
     * 初始化LevelDB
     * 每次使用levelDB前都要调用此方法，无论db是否存在
     */
    private static void initLevelDB() {
        DBFactory factory = new Iq80DBFactory();
        Options options = new Options();
        options.createIfMissing(true);
        try {
            db = factory.open(new File(dbFolder), options);
        } catch (IOException e) {
            System.out.println("levelDB启动异常");
            e.printStackTrace();
        }
    }

    /**
     * 基于fastjson的对象序列化
     *
     * @param obj
     * @return
     */
    private static byte[] serializer(Object obj) {
        byte[] jsonBytes = JSON.toJSONBytes(obj);
        return jsonBytes;

    }

    /**
     * 基于fastJson的对象反序列化
     *
     * @param bytes
     * @return
     */
    private static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        String str = new String(bytes);
        return JSONObject.parseObject(str, clazz);
    }

    /**
     * 存放数据
     *
     * @param key
     * @param val
     */
    public static void put(String key, Object val) {
        try {
            db.put(key.getBytes(charset), serializer(val));
        } catch (UnsupportedEncodingException e) {
            System.out.println("编码转化异常");
            e.printStackTrace();
        }
    }

    /**
     * 根据key获取数据
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        byte[] val = null;
        try {
            val = db.get(key.getBytes(charset));
        } catch (Exception e) {
            System.out.println("levelDB get error");
            e.printStackTrace();
            return null;
        }
        if (val == null) {
            return null;
        }
        return deserializer(val, clazz);
    }

    /**
     * 根据key删除数据
     *
     * @param key
     */
    public static void delete(String key) {
        try {
            db.delete(key.getBytes(charset));
        } catch (Exception e) {
            System.out.println("levelDB delete error");
            e.printStackTrace();
        }
    }


    /**
     * 关闭数据库连接
     * 每次只要调用了initDB方法，就要在最后调用此方法
     */
    public static void closeDB() {
        if (db != null) {
            try {
                db.close();
            } catch (IOException e) {
                System.out.println("levelDB 关闭异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取所有key
     *
     * @return
     */
    public static List<String> getKeys() {

        List<String> list = new ArrayList<>();
        DBIterator iterator = null;
        try {
            iterator = db.iterator();
            while (iterator.hasNext()) {
                Map.Entry<byte[], byte[]> item = iterator.next();
                String key = new String(item.getKey(), charset);
                list.add(key);
            }
        } catch (Exception e) {
            System.out.println("遍历发生异常");
            e.printStackTrace();
        } finally {
            if (iterator != null) {
                try {
                    iterator.close();
                } catch (IOException e) {
                    System.out.println("遍历发生异常");
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    public static void main(String[] args) {
//        LevelDbUtils levelDbUtil=new LevelDbUtils();
//        levelDbUtil.initLevelDB();
//        levelDbUtil.put("name","keer");
//        User user=new User("keer","北京市");
//        levelDbUtil.put("userInfo",user);
//        System.out.println("获得数据库中的所有key" + levelDbUtil.getKeys().toString());
//        System.out.println("数据库中key：name，value："+levelDbUtil.get("name").toString());
//        System.out.println("数据库中key：userInfo，value："+levelDbUtil.get("userInfo").toString());
//        levelDbUtil.closeDB();
    }

}

