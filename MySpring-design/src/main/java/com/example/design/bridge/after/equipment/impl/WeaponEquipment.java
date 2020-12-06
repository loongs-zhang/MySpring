package com.example.design.bridge.after.equipment.impl;

import com.example.design.bridge.after.equipment.Equipment;

/**
 * 具体实现角色
 *
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class WeaponEquipment implements Equipment {
    @Override
    public void show() {
        System.out.println("武器");
    }
}
