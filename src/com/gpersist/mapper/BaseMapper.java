package com.gpersist.mapper;

import java.util.List;

public interface BaseMapper {
    public <T, X> List<T> GetList(X params);

    public <T, X> T Get(X params);
}
