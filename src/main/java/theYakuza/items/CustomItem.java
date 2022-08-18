package theYakuza.items;

import com.megacrit.cardcrawl.helpers.ImageMaster;

public class CustomItem extends AbstractItem {
    public int upgrades;

    public CustomItem(String ID,
            String NAME,
            int durability,
            int attackValue,
            int skillValue,
            int throwValue,
            String imgPath) {
        this.ID = ID;
        this.name = NAME;
        this.durability = durability;
        this.baseAttackValue = this.attackValue = attackValue;
        this.baseSkillValue = this.skillValue = skillValue;
        this.baseThrowValue = this.throwValue = throwValue;
        this.img = ImageMaster.loadImage(imgPath);
        this.applyCustomPowers();
        this.updateDescription();
        this.restoreValues();
    }

    public void upgrade(int times, int attack_up, int skill_up, int throw_up) {
        this.baseAttackValue += attack_up * times;
        this.baseSkillValue += skill_up * times;
        this.baseThrowValue += throw_up * times;
        this.attackValue = baseAttackValue;
        this.skillValue = baseSkillValue;
        this.throwValue = baseThrowValue;
        this.upgrades = times;
        this.applyCustomPowers();
        this.updateDescription();
        this.restoreValues();
    }

}
