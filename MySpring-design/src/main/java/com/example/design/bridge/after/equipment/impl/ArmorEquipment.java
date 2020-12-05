package com.example.design.bridge.after.equipment.impl;

import com.example.design.bridge.after.equipment.Equipment;

/**
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class ArmorEquipment implements Equipment {
    @Override
    public void show() {
        System.out.println("我装备了防具");
    }
}
