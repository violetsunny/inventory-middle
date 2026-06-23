package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 文件导入记录领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class FileImportRecord implements Entity<FileImportRecord> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件key
     */
    private String fileKey;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 处理状态
     */
    private String processStatus;

    /**
     * 实现类型
     */
    private String implType;

    /**
     * 总数
     */
    private Integer totalNum;

    /**
     * 成功数
     */
    private Integer successNum;

    /**
     * 失败数
     */
    private Integer failNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean sameIdentityAs(FileImportRecord other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
