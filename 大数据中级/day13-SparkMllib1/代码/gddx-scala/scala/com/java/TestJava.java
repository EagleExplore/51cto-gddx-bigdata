package com.java;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 3/25.
 */
public class TestJava {

    public TestJava()
    {

    }
    public TestJava(String a)
    {

    }
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(new Date());

    }
}
