package xyz.maywr.hack.api.mixin.accessors;

public interface IPlayerControllerMP {
    void setIsHittingBlock(boolean b);
    void setBlockHitDelay(int delay);
    float getCurBlockDamageMP();
}
