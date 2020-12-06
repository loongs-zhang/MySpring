package com.example.design.bridge.after;

import com.example.design.bridge.after.equipment.Equipment;
import com.example.design.bridge.after.skill.Skill;

/**
 * 修正抽象角色
 *
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class Hero extends AbstractHero {
    @Override
    public void show() {
        System.out.println("我装备了以下装备：");
        for (Equipment equipment : equipments) {
            equipment.show();
        }
        System.out.println("我会释放以下技能：");
        for (Skill skill : skills) {
            skill.show();
        }
    }
}
