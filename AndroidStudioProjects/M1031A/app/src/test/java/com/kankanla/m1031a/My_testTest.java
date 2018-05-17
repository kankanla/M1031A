package com.kankanla.m1031a;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kankanla on 2018/01/31.
 */
public class My_testTest {

    @Test
    public void x(){




    }

    @Test
    public void y(){
        System.out.println("xxxxxxxxxxxxxxxxx");
        My_test my_test = new My_test() {
            @Override
            public void x(String temp) {
                super.x(temp);
                System.out.println(temp);
            }

            @Override
            public void abc() {
                super.abc();
            }
        };

        my_test.x("xxx");

    }



}