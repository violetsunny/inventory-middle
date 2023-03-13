/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.handles;

/**
 * @author kll
 * @version $Id: IHandler, v 0.1 2021/6/20 20:01 Exp $
 */
public interface IHandler {

    <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message);

}
