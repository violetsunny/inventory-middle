package com.inventory.middle.infra.plan.persistence.condition.plan;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public class PageCondition {

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private Integer pageNum;

    private Integer size;

    private List<Sort> sorts;

    public Integer getOffset() {
        return (getPageNum() - 1) * getSize();
    }

    public void appendAsc(String key) {
        if (null == sorts) {
            sorts = Lists.newArrayList();
        }
        sorts.add(Sort.ofAsc(key));
    }

    public void appendDesc(String key) {
        if (null == sorts) {
            sorts = Lists.newArrayList();
        }
        sorts.add(Sort.ofDesc(key));
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public Integer getPageNum() {
        if (null == pageNum || pageNum <= 0) {
            this.pageNum = 1;
        }
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getSize() {
        if (null == size || size <= 0) {
            this.size = DEFAULT_PAGE_SIZE;
        }
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Getter
    public static class Sort {

        private final String key;

        private final Order order;

        private Sort(String key, Order order) {
            this.key = key;
            this.order = order;
        }

        public static Sort ofAsc(String key) {
            return of(key, Order.ASC);
        }

        public static Sort ofDesc(String key) {
            return of(key, Order.DESC);
        }

        private static Sort of(String key, Order order) {
            return new Sort(key, order);
        }
    }

    private enum Order {
        ASC,
        DESC,
    }
}
