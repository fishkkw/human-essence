package com.mrk.util;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author wangkai
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2021
 * @date 2021/1/13,13:38
 */
public class RobotUtil {

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
            Robot myRobot = new Robot();
            PointerInfo pinfo = MouseInfo.getPointerInfo();
            Point p = pinfo.getLocation();
            System.out.println("p.getX()：" + p.getX() + " , p.getY()" + +p.getY());
            myRobot.mouseMove(618, 534);

            //鼠标左键点击
            myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            //松开
            myRobot.mouseRelease(KeyEvent.BUTTON1_MASK);
            myRobot.setAutoDelay(200);
            myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            myRobot.mouseRelease(KeyEvent.BUTTON1_MASK);
            myRobot.setAutoDelay(3000);
            myRobot.mouseMove(1327, 959);
            myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            myRobot.mouseRelease(KeyEvent.BUTTON1_MASK);
            myRobot.setAutoDelay(200);
            myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            myRobot.mouseRelease(KeyEvent.BUTTON1_MASK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
