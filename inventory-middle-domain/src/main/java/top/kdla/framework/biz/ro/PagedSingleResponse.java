package top.kdla.framework.biz.ro;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应 RO 包装类（接口层使用）
 * <p>
 * 别名类，与 top.kdla.framework.dto.PagedSingleResponse 功能相同。
 * 在当前 KDLA 版本中不存在对应的 jar 类，因此在此项目中作为本地替代实现。
 * </p>
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

    public static <T extends Serializable> PagedSingleResponse<T> success(long pageNum, long pageSize, long totalCount, List<T> data) {
        PagedSingleResponse<T> response = new PagedSingleResponse<>();
        response.success = true;
        response.pageNum = pageNum;
        response.pageSize = pageSize;
        response.totalCount = totalCount;
        response.data = data != null ? data : Collections.emptyList();
        return response;
    }

    public static <T extends Serializable> PagedSingleResponse<T> failure(String errCode, String errMessage) {
        PagedSingleResponse<T> response = new PagedSingleResponse<>();
        response.success = false;
        response.errCode = errCode;
        response.errMessage = errMessage;
        response.data = Collections.emptyList();
        return response;
    }

    public boolean isSuccess() { return success; }
    public long getPageNum() { return pageNum; }
    public void setPageNum(long pageNum) { this.pageNum = pageNum; }
    public long getPageSize() { return pageSize; }
    public void setPageSize(long pageSize) { this.pageSize = pageSize; }
    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
    public List<T> getData() { return data; }
    public void setData(List<T> data) { this.data = data; }
    public String getErrCode() { return errCode; }
    public String getErrMessage() { return errMessage; }
}
