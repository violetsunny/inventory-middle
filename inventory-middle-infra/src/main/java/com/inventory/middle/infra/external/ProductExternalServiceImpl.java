package com.inventory.middle.infra.external;

import com.inventory.middle.client.dto.sku.SkuBatchPageRequest;
import com.inventory.middle.client.dto.sku.SkuBatchResponse;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.inventory.middle.domain.service.external.dto.SkuBatchRequest;
import com.inventory.middle.domain.service.external.dto.SkuResponse;
import com.inventory.middle.infra.persistence.entity.SkuBatchDo;
import com.inventory.middle.infra.persistence.entity.SkuBatchQueryPO;
import com.inventory.middle.infra.persistence.mapper.SkuBatchMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品中心服务 Infra 实现（本地 DB 版）
 * <p>
 * 使用项目内沉淀的 product_sku_batch 表替代外部 HTTP 调用。
 * 数据写入：通过 SkuBatchMapper.batchInsertOrUpdate 同步 SKU 批次数据。
 */
@Slf4j
@Service
public class ProductExternalServiceImpl implements RemoteProductCenterRestService {

    @Resource
    private SkuBatchMapper skuBatchMapper;

    @Override
    public SingleResponse<Object> skuBatchSys(List<SkuBatchRequest> reqs, String token, String tenantId) {
        if (CollectionUtils.isEmpty(reqs)) {
            return SingleResponse.buildSuccess(null);
        }
        List<SkuBatchDo> doList = reqs.stream().map(req -> {
            SkuBatchDo skuBatchDo = new SkuBatchDo();
            skuBatchDo.setSkuCode(req.getSkuCode());
            skuBatchDo.setBatchCode(req.getBatchCode());
            skuBatchDo.setTenantId(tenantId);
            return skuBatchDo;
        }).collect(Collectors.toList());
        skuBatchMapper.batchInsertOrUpdate(doList);
        log.info("ProductExternalServiceImpl.skuBatchSys synced size={} tenantId={}", doList.size(), tenantId);
        return SingleResponse.buildSuccess(null);
    }

    @Override
    public List<com.inventory.middle.domain.service.external.dto.SkuBatchResponse> skuBatchListByRequest(
            com.inventory.middle.domain.service.external.dto.SkuBatchPageRequest request,
            String token, String tenantId) {
        if (request == null) return Collections.emptyList();
        SkuBatchQueryPO param = new SkuBatchQueryPO();
        param.setSkuCode(request.getSkuCode());
        param.setTenantId(tenantId);
        List<SkuBatchDo> doList = skuBatchMapper.list(param);
        return doList.stream().map(d -> {
            com.inventory.middle.domain.service.external.dto.SkuBatchResponse resp =
                    new com.inventory.middle.domain.service.external.dto.SkuBatchResponse();
            BeanUtils.copyProperties(d, resp);
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SkuResponse> skuListByRequest(List<String> skuCodes, String token, String tenantId) {
        if (CollectionUtils.isEmpty(skuCodes)) return Collections.emptyList();
        SkuBatchQueryPO param = new SkuBatchQueryPO();
        param.setSkuCodeList(skuCodes);
        param.setTenantId(tenantId);
        List<SkuBatchDo> doList = skuBatchMapper.list(param);
        return doList.stream().map(d -> {
            SkuResponse resp = new SkuResponse();
            BeanUtils.copyProperties(d, resp);
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SkuBatchResponse> skuBatchListByRequestWithSpecialToken(
            SkuBatchPageRequest request, String token, String operatorId, String tenantId) {
        if (request == null) return Collections.emptyList();
        SkuBatchQueryPO param = new SkuBatchQueryPO();
        if (request.getPager() != null) {
            param.setPageNum((request.getPager().getPage() - 1) * request.getPager().getSize());
            param.setPageSize(request.getPager().getSize());
        }
        if (StringUtils.hasText(request.getSkuCode())) {
            param.setSkuCode(request.getSkuCode());
        }
        param.setTenantId(tenantId);
        List<SkuBatchDo> doList = skuBatchMapper.list(param);
        log.info("ProductExternalServiceImpl.skuBatchListByRequestWithSpecialToken local DB, tenantId={} size={}", tenantId, doList.size());
        return doList.stream().map(d -> {
            SkuBatchResponse resp = new SkuBatchResponse();
            resp.setSkuCode(d.getSkuCode());
            resp.setBatchCode(d.getBatchCode());
            resp.setTenantId(d.getTenantId());
            resp.setExt(d.getExt());
            resp.setUpdatorId(d.getUpdatorId());
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public SingleResponse<Object> queryBuildMaterialInfo(String skuCode, String tenantId) {
        if (!StringUtils.hasText(skuCode)) {
            return SingleResponse.buildSuccess(null);
        }
        SkuBatchQueryPO param = new SkuBatchQueryPO();
        param.setSkuCode(skuCode);
        param.setTenantId(tenantId);
        List<SkuBatchDo> doList = skuBatchMapper.list(param);
        log.info("ProductExternalServiceImpl.queryBuildMaterialInfo local DB, skuCode={} tenantId={} size={}", skuCode, tenantId, doList.size());
        if (CollectionUtils.isEmpty(doList)) {
            return SingleResponse.buildSuccess(null);
        }
        SkuBatchDo first = doList.get(0);
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("skuCode", first.getSkuCode());
        result.put("batchCode", first.getBatchCode());
        result.put("tenantId", first.getTenantId());
        result.put("ext", first.getExt());
        return SingleResponse.buildSuccess(result);
    }

    @Override
    public SingleResponse<Object> fuzzyQueryByName(String skuName, int pageNum, int pageSize, String tenantId) {
        // 本地 SkuBatchDo 无 skuName 字段，降级按租户分页返回全量数据
        SkuBatchQueryPO param = new SkuBatchQueryPO();
        param.setTenantId(tenantId);
        int offset = (pageNum - 1) * pageSize;
        param.setPageNum(offset);
        param.setPageSize(pageSize);
        List<SkuBatchDo> doList = skuBatchMapper.list(param);
        log.info("ProductExternalServiceImpl.fuzzyQueryByName degraded local DB, skuName={} tenantId={} size={}", skuName, tenantId, doList.size());
        List<java.util.Map<String, Object>> items = doList.stream().map(d -> {
            java.util.Map<String, Object> item = new java.util.HashMap<>();
            item.put("skuCode", d.getSkuCode());
            item.put("batchCode", d.getBatchCode());
            item.put("tenantId", d.getTenantId());
            item.put("ext", d.getExt());
            return item;
        }).collect(Collectors.toList());
        return SingleResponse.buildSuccess(items);
    }
}
