package com.example.design.bridge.before.skill;

import com.example.design.bridge.before.equipment.HeroWithWeaponEquipment;

/**
 * @author SuccessZhang
 * @date 2020/12/05
 */
public class HeroWithDisplacementSkill extends HeroWithWeaponEquipment {
    @Override
    public void show() {
        super.show();
        System.out.println("我会释放位移技能");
    }
}
