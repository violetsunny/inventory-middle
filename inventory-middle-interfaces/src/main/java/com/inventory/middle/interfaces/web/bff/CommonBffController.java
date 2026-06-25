package com.inventory.middle.interfaces.web.bff;

import com.inventory.middle.application.service.InventoryCommonApplicationService;
import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "BFF 兼容：通用功能接口（/common）")
@CatchAndLog
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonBffController {

    @Resource
    private InventoryCommonApplicationService inventoryCommonApplicationService;

    @Operation(summary = "省查询（BFF 兼容 stub）")
    @PostMapping("/province")
    public SingleResponse<List<Object>> province() {
        log.warn("BFF 兼容路由 /common/province：依赖 masterdata-location，返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "市查询（BFF 兼容 stub）")
    @PostMapping("/city")
    public SingleResponse<List<Object>> city(@RequestBody(required = false) Map<String, String> request) {
        log.warn("BFF 兼容路由 /common/city：依赖 masterdata-location，返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "区查询（BFF 兼容 stub）")
    @PostMapping("/region")
    public SingleResponse<List<Object>> region(@RequestBody(required = false) Map<String, String> request) {
        log.warn("BFF 兼容路由 /common/region：依赖 masterdata-location，返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "租户组织结构查询（BFF 兼容）")
    @PostMapping("/companyTree")
    public PageResponse<CompanyTreeBffVO> organizationTree(@RequestBody(required = false) Object request) {
        List<ParticipantDeptDTO> tree = inventoryCommonApplicationService.getTenantDeptTree(
                UserContextHolder.getTenantId(), UserContextHolder.getUserId());
        List<CompanyTreeBffVO> result = tree.stream().map(this::toTreeVO).collect(Collectors.toList());
        return PageResponse.buildSuccess(result);
    }

    @Operation(summary = "租户查询公司列表（BFF 兼容）")
    @PostMapping("/listCompany")
    public PageResponse<CompanyTreeBffVO> listCompany() {
        List<ParticipantDeptDTO> tree = inventoryCommonApplicationService.getTenantDeptTree(
                UserContextHolder.getTenantId(), UserContextHolder.getUserId());
        List<CompanyTreeBffVO> flat = new ArrayList<>();
        tree.forEach(node -> flatten(node, flat));
        return PageResponse.buildSuccess(flat);
    }

    @Operation(summary = "货币枚举（BFF 兼容）")
    @PostMapping("/currency")
    public SingleResponse<List<Object>> currency() {
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "结算方式枚举（BFF 兼容）")
    @PostMapping("/settlementType")
    public SingleResponse<List<Object>> settlementType() {
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "菜单（BFF 兼容 stub）")
    @PostMapping("/getMenus")
    public SingleResponse<List<Object>> getMenus() {
        log.warn("BFF 兼容路由 /common/getMenus：middle 暂未实现菜单服务，返回空列表");
        return SingleResponse.buildSuccess(Collections.emptyList());
    }

    @Operation(summary = "当前登录用户信息（BFF 兼容）")
    @PostMapping("/getCurrentUserInfo")
    public SingleResponse<CurrentUserInfoBffVO> getCurrentUserInfo() {
        CurrentUserInfoBffVO vo = new CurrentUserInfoBffVO();
        vo.setUserId(UserContextHolder.getUserId());
        vo.setUserName(UserContextHolder.getUsername());
        vo.setTenantId(UserContextHolder.getTenantId());
        return SingleResponse.buildSuccess(vo);
    }

    private CompanyTreeBffVO toTreeVO(ParticipantDeptDTO dto) {
        if (dto == null) return null;
        CompanyTreeBffVO vo = new CompanyTreeBffVO();
        vo.setParticipantName(dto.getGroupName());
        vo.setParticipantId(dto.getId());
        if (dto.getChildren() != null) {
            vo.setChildren(dto.getChildren().stream().map(this::toTreeVO).collect(Collectors.toList()));
        }
        return vo;
    }

    private void flatten(ParticipantDeptDTO node, List<CompanyTreeBffVO> result) {
        if (node == null) return;
        result.add(toTreeVO(node));
        if (node.getChildren() != null) {
            node.getChildren().forEach(child -> flatten(child, result));
        }
    }

    @Data
    public static class CompanyTreeBffVO implements Serializable {
        private String participantName;
        private String participantId;
        private List<CompanyTreeBffVO> children;
    }

    @Data
    public static class CurrentUserInfoBffVO implements Serializable {
        private String userId;
        private String userName;
        private String tenantId;
    }
}
