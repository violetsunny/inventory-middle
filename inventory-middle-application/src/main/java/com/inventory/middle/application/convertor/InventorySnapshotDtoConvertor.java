package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.command.InventorySnapshotCommand;
import com.inventory.middle.infra.persistence.entity.InventorySnapshotDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存快照-实时DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface InventorySnapshotDtoConvertor {

    /**
     * dto --> entity
     * @param inventorysnapshotDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventorySnapshot toInventorySnapshot(final InventorySnapshotDto inventorysnapshotDto);

    /**
     * dto --> entity
     * @param inventorysnapshotCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventorySnapshot toInventorySnapshot(final InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * entity --> dto
     * @param inventorysnapshot
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventorySnapshotDto fromInventorySnapshot(final InventorySnapshot inventorysnapshot);

    /**
     * cmd --> dto
     * @param inventorysnapshotCommand
     * @return
     */
      InventorySnapshotDto fromInventorySnapshotCommand(final InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * do --> dto
     * @param inventorysnapshotDo
     * @return
     */
      InventorySnapshotDto fromDo(final InventorySnapshotDo inventorysnapshotDo);

      default InventorySnapshotId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventorySnapshotId(id);
      }

      default Long id2id(InventorySnapshotId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}