package com.weather.bigdata.it.app.check_spring.service;

import com.weather.bigdata.it.app.check_spring.utils.status.ObjStatus;

public interface CollectionMsg {
    /**
     *
     * @param watchObject
     * @param os
     * @return 返回状态是否发生改变
     */
    Boolean add(String watchObject, ObjStatus os);

    /**
     *
     * @return 发现信息及提示语
     */
    String remainStatus();
}
