package com.inventory.middle.domain.common.util;

import com.inventory.middle.domain.model.bo.BaseBO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/** 通用 BO 帮助工具（迁移自 inventory-center CommonHelper） */
public class CommonHelper {

    public static <T extends BaseBO> List<T> diff(List<T> r1, List<T> r2) {
        Set<Long> s1 = Optional.ofNullable(r1).orElse(Lists.newArrayList())
                .stream().filter(Objects::nonNull).map(BaseBO::getId).collect(Collectors.toSet());
        Set<Long> s2 = Optional.ofNullable(r2).orElse(Lists.newArrayList())
                .stream().filter(Objects::nonNull).map(BaseBO::getId).collect(Collectors.toSet());
        Set<Long> diff = Sets.difference(s1, s2);
        return Optional.ofNullable(r1).orElse(Lists.newArrayList()).stream()
                .filter(e -> diff.contains(e.getId())).collect(Collectors.toList());
    }
}