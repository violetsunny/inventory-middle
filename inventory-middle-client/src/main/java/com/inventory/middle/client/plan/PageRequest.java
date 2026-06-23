package com.inventory.middle.client.plan;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageRequest implements Serializable {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final long serialVersionUID = -9160730421935197853L;
    private Integer pageNum;
    private Integer pageSize;
    private List<Sort> sorts;

    public Integer offset() { return (this.getPageNum() - 1) * getPageSize(); }
    public void appendAsc(String key) { if (sorts == null) sorts = new ArrayList<>(); sorts.add(Sort.ofAsc(key)); }
    public void appendDesc(String key) { if (sorts == null) sorts = new ArrayList<>(); sorts.add(Sort.ofDesc(key)); }
    public Integer getPageNum() { if (null == pageNum || pageNum <= 0) this.pageNum = 1; return this.pageNum; }
    public Integer getPageSize() { if (null == pageSize || pageSize <= 0) this.pageSize = DEFAULT_PAGE_SIZE; return this.pageSize; }

    public static class Sort {
        private final String key;
        private final Order order;

        private Sort(String key, Order order) {
            this.key = key;
            this.order = order;
        }

        public String getKey() { return this.key; }
        public String getOrder() { return this.order.name(); }
        public static Sort ofAsc(String key) { return new Sort(key, Order.ASC); }
        public static Sort ofDesc(String key) { return new Sort(key, Order.DESC); }
    }

    public enum Order { ASC, DESC }
}
