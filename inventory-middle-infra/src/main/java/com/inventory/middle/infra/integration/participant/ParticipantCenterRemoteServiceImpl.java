package com.inventory.middle.infra.integration.participant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import com.inventory.middle.domain.service.external.ParticipantCenterRemoteService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParticipantCenterRemoteServiceImpl implements ParticipantCenterRemoteService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${remote.participant.search.url:}")
    private String searchUrl;

    @Value("${remote.participant.tenant.url:}")
    private String tenantUrl;

    @Value("${remote.participant.identify.url:}")
    private String identifyUrl;

    @Value("${remote.participant.appId:inventory-middle}")
    private String appId;

    @Override
    public List<ParticipantUser> fuzzyQueryUserInfo(String keywords) {
        if (notConfigured(searchUrl)) return Collections.emptyList();
        try {
            String url = UriComponentsBuilder.fromHttpUrl(searchUrl + "/account/keywords")
                    .queryParam("keywords", keywords)
                    .queryParam("records", 10)
                    .toUriString();
            String body = get(url);
            BaseResponse<List<ParticipantUser>> resp = JSON.parseObject(body,
                    new TypeReference<BaseResponse<List<ParticipantUser>>>() {});
            return resp != null && resp.getData() != null ? resp.getData() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("fuzzyQueryUserInfo failed, keywords={}", keywords, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ParticipantDeptDTO> getTenantDeptTree(String tenantId, String userId) {
        if (notConfigured(tenantUrl)) return Collections.emptyList();
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(tenantUrl + "/TenantManage/tenant/department/getDeptInfoByUserTenantAndTypeCode")
                    .queryParam("tenantId", tenantId)
                    .queryParam("accountId", userId)
                    .queryParam("typeCode", "company")
                    .toUriString();
            String body = get(url);
            BaseResponse<List<ParticipantDeptDTO>> resp = JSON.parseObject(body,
                    new TypeReference<BaseResponse<List<ParticipantDeptDTO>>>() {});
            return resp != null && resp.getData() != null ? resp.getData() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("getTenantDeptTree failed, tenantId={}, userId={}", tenantId, userId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public String getCompanyName(String tenantId, String userId, String companyCode) {
        List<ParticipantDeptDTO> tree = getTenantDeptTree(tenantId, userId);
        if (tree.isEmpty()) return companyCode;
        List<ParticipantDeptDTO> flat = new ArrayList<>();
        tree.forEach(node -> flatten(node, flat));
        return flat.stream()
                .filter(d -> companyCode.equals(d.getId()))
                .map(ParticipantDeptDTO::getGroupName)
                .findFirst()
                .orElse(companyCode);
    }

    @Override
    public List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId) {
        if (notConfigured(identifyUrl)) return Collections.emptyList();
        try {
            String url = identifyUrl + "/AuthIdentify/app/menuAndFunc/" + userId + "/" + tenantId + "/" + appId;
            String body = get(url);
            BaseResponse<List<ParticipantMenuDTO>> resp = JSON.parseObject(body,
                    new TypeReference<BaseResponse<List<ParticipantMenuDTO>>>() {});
            return resp != null && resp.getData() != null ? resp.getData() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("getMenuAndFunc failed, tenantId={}, userId={}", tenantId, userId, e);
            return Collections.emptyList();
        }
    }

    private String get(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // TODO: 接入 participant-token 认证（需 ParticipantTokenService 或网关透传）
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return entity.getBody();
    }

    private boolean notConfigured(String url) {
        if (StringUtils.isBlank(url)) {
            log.warn("ParticipantCenter URL not configured");
            return true;
        }
        return false;
    }

    private void flatten(ParticipantDeptDTO node, List<ParticipantDeptDTO> result) {
        if (node == null) return;
        result.add(node);
        if (node.getChildren() != null) {
            node.getChildren().forEach(child -> flatten(child, result));
        }
    }

    @Data
    static class BaseResponse<T> {
        private Integer code;
        private String msg;
        private T data;
    }
}
