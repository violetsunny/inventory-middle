package com.inventory.middle.interfaces.web.common;

import com.inventory.middle.client.enums.CurrencyEnum;
import com.inventory.middle.client.enums.SettlementTypeEnum;
import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;
import com.inventory.middle.application.service.InventoryCommonApplicationService;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "通用功能接口（inventory-center-bff 迁移）")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/inventory-common")
public class InventoryCommonController {

    @Resource
    private InventoryCommonApplicationService inventoryCommonApplicationService;

    @Operation(summary = "租户组织结构查询")
    @PostMapping("/companyTree")
    public SingleResponse<List<CompanyTreeVO>> companyTree() {
        List<ParticipantDeptDTO> tree = inventoryCommonApplicationService.getTenantDeptTree(
                UserContextHolder.getTenantId(), UserContextHolder.getUserId());
        List<CompanyTreeVO> result = tree.stream().map(this::toTreeVO).collect(Collectors.toList());
        return SingleResponse.buildSuccess(result);
    }

    @Operation(summary = "租户查询公司列表")
    @PostMapping("/listCompany")
    public SingleResponse<List<CompanyTreeVO>> listCompany() {
        List<ParticipantDeptDTO> tree = inventoryCommonApplicationService.getTenantDeptTree(
                UserContextHolder.getTenantId(), UserContextHolder.getUserId());
        List<CompanyTreeVO> flat = new ArrayList<>();
        tree.forEach(node -> flatten(node, flat));
        return SingleResponse.buildSuccess(flat);
    }

    @Operation(summary = "货币枚举")
    @PostMapping("/currency")
    public SingleResponse<List<KeyValueVO<String>>> currency() {
        List<KeyValueVO<String>> list = Arrays.stream(CurrencyEnum.values())
                .map(e -> new KeyValueVO<>(e.getCode(), e.getDescription()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "结算方式枚举")
    @PostMapping("/settlementType")
    public SingleResponse<List<KeyValueVO<Integer>>> settlementType() {
        List<KeyValueVO<Integer>> list = Arrays.stream(SettlementTypeEnum.values())
                .map(e -> new KeyValueVO<>(e.getCode(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "当前登录用户信息")
    @PostMapping("/getCurrentUserInfo")
    public SingleResponse<CurrentUserInfoVO> getCurrentUserInfo() {
        CurrentUserInfoVO vo = new CurrentUserInfoVO();
        vo.setUserId(UserContextHolder.getUserId());
        vo.setUserName(UserContextHolder.getUsername());
        vo.setTenantId(UserContextHolder.getTenantId());
        return SingleResponse.buildSuccess(vo);
    }

    @Operation(summary = "省查询")
    @PostMapping("/province")
    public SingleResponse<List<KeyValueVO<String>>> province() {
        log.warn("province 接口依赖 masterdata-location 服务，当前返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "市查询")
    @PostMapping("/city")
    public SingleResponse<List<KeyValueVO<String>>> city(@RequestBody Map<String, String> request) {
        log.warn("city 接口依赖 masterdata-location 服务，当前返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "区查询")
    @PostMapping("/region")
    public SingleResponse<List<KeyValueVO<String>>> region(@RequestBody Map<String, String> request) {
        log.warn("region 接口依赖 masterdata-location 服务，当前返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    private CompanyTreeVO toTreeVO(ParticipantDeptDTO dto) {
        if (dto == null) return null;
        CompanyTreeVO vo = new CompanyTreeVO();
        vo.setParticipantName(dto.getGroupName());
        vo.setParticipantId(dto.getId());
        if (dto.getChildren() != null) {
            vo.setChildren(dto.getChildren().stream().map(this::toTreeVO).collect(Collectors.toList()));
        }
        return vo;
    }

    private void flatten(ParticipantDeptDTO node, List<CompanyTreeVO> result) {
        if (node == null) return;
        result.add(toTreeVO(node));
        if (node.getChildren() != null) {
            node.getChildren().forEach(child -> flatten(child, result));
        }
    }

    @Data
    public static class CompanyTreeVO implements Serializable {
        private String participantName;
        private String participantId;
        private List<CompanyTreeVO> children;
    }

    @Data
    public static class KeyValueVO<T> implements Serializable {
        private T key;
        private String value;
        public KeyValueVO(T key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @Data
    public static class CurrentUserInfoVO implements Serializable {
        private String userId;
        private String userName;
        private String tenantId;
    }
}
