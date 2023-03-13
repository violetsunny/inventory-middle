package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.model.types.WarehouseId;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import com.inventory.middle.infra.persistence.entity.WarehouseDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物理仓库表DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface WarehouseDtoConvertor {

    /**
     * dto --> entity
     * @param warehouseDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    Warehouse toWarehouse(final WarehouseDto warehouseDto);

    /**
     * dto --> entity
     * @param warehouseCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     Warehouse toWarehouse(final WarehouseCommand warehouseCommand);

    /**
     * entity --> dto
     * @param warehouse
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     WarehouseDto fromWarehouse(final Warehouse warehouse);

    /**
     * cmd --> dto
     * @param warehouseCommand
     * @return
     */
      WarehouseDto fromWarehouseCommand(final WarehouseCommand warehouseCommand);

    /**
     * do --> dto
     * @param warehouseDo
     * @return
     */
      WarehouseDto fromDo(final WarehouseDo warehouseDo);

      default WarehouseId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new WarehouseId(id);
      }

      default Long id2id(WarehouseId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}