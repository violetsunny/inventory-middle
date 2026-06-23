package top.kdla.framework.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * 分页响应 BO 包装类
 * <p>
 * 用于应用层分页数据的封装，类似 PageResponse 但用于 BO 层（不是 REST 响应层）。
 * 迁移自 scm-plan-management 的同名类，在当前 KDLA 版本中不存在对应的 jar 类，
 * 因此在此项目中作为本地替代实现。
 * </p>
 *
 * @param <T> 数据类型
 * @author migrated from scm-plan-management
 */
public class PagedSingleResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long pageNum;
    private long pageSize;
    private long totalCount;
    private List<T> data;
    private boolean success;
    private String errCode;
    private String errMessage;

    public PagedSingleResponse() {
    }

    /**
     * 构建成功的分页响应
     *
     * @param pageNum    当前页码
     * @param pageSize   每页大小
     * @param totalCount 总数量
     * @param data       数据列表
     * @param <T>        数据类型
     * @return 分页响应
     */
    public static <T extends Serializable> PagedSingleResponse<T> success(long pageNum, long pageSize, long totalCount, List<T> data) {
        PagedSingleResponse<T> response = new PagedSingleResponse<>();
        response.success = true;
        response.pageNum = pageNum;
        response.pageSize = pageSize;
        response.totalCount = totalCount;
        response.data = data != null ? data : Collections.emptyList();
        return response;
    }

    /**
     * 构建失败响应
     *
     * @param errCode    错误码
     * @param errMessage 错误信息
     * @param <T>        数据类型
     * @return 失败响应
     */
    public static <T extends Serializable> PagedSingleResponse<T> failure(String errCode, String errMessage) {
        PagedSingleResponse<T> response = new PagedSingleResponse<>();
        response.success = false;
        response.errCode = errCode;
        response.errMessage = errMessage;
        response.data = Collections.emptyList();
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
