package com.example.design.bridge.after;

import com.example.design.bridge.after.equipment.Equipment;
import com.example.design.bridge.after.skill.Skill;

/**
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class Hero extends AbstractHero {
    @Override
    public void show() {
        for (Equipment equipment : equipments) {
            equipment.show();
        }
        for (Skill skill : skills) {
            skill.show();
        }
    }
}
