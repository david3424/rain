package org.david.rain.wmproxy.common.core;

import org.david.rain.wmproxy.common.base.hibernate3.BaseManagerImpl;

import java.io.Serializable;


public class CoreManagerImpl<T extends Serializable> extends
        BaseManagerImpl<T> implements CoreManager<T> {
}
