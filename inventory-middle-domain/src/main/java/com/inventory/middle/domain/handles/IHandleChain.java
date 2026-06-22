/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.inventory.middle.domain.handles;


/**
 * 
 * @author xy23087
 * @version $Id: IProcessChain.java, v 0.1 2017年2月3日 下午2:01:55 xy23087 Exp $
 */
public interface IHandleChain<T, E> {
    /**
     * 操作
     * @param msg
     * @return
     */
    boolean doProcess(HandleMessage<T, E> msg);
}
