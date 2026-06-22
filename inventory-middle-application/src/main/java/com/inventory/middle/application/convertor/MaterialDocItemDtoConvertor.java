package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.model.types.MaterialDocItemId;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.command.MaterialDocItemCommand;
import com.inventory.middle.infra.persistence.entity.MaterialDocItemDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-itemDtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface MaterialDocItemDtoConvertor {

    /**
     * dto --> entity
     * @param materialdocitemDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MaterialDocItem toMaterialDocItem(final MaterialDocItemDto materialdocitemDto);

    /**
     * dto --> entity
     * @param materialdocitemCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MaterialDocItem toMaterialDocItem(final MaterialDocItemCommand materialdocitemCommand);

    /**
     * entity --> dto
     * @param materialdocitem
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MaterialDocItemDto fromMaterialDocItem(final MaterialDocItem materialdocitem);

    /**
     * cmd --> dto
     * @param materialdocitemCommand
     * @return
     */
      MaterialDocItemDto fromMaterialDocItemCommand(final MaterialDocItemCommand materialdocitemCommand);

    /**
     * do --> dto
     * @param materialdocitemDo
     * @return
     */
      MaterialDocItemDto fromDo(final MaterialDocItemDo materialdocitemDo);

      default MaterialDocItemId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MaterialDocItemId(id);
      }

      default Long id2id(MaterialDocItemId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}