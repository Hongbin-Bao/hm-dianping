package com.hmdp.utils;

/**
 * @author Hongbin BAO
 * @Date 2023/12/19 17:33
 */
public interface ILock {


    /**
     *  尝试获取锁
     * @param timeoutSec 锁持有的超时时间 过期后自动释放
     * @return true 代表获取锁成功 false 代表获取锁失败
     */
    boolean tryLock(long timeoutSec);

    /**
     *  释放锁
     */
    void unlock();
}
