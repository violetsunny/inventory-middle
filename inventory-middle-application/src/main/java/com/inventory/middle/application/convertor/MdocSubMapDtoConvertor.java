package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.model.types.MdocSubMapId;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.command.MdocSubMapCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubMapDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-移动平均价DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface MdocSubMapDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubmapDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubMap toMdocSubMap(final MdocSubMapDto mdocsubmapDto);

    /**
     * dto --> entity
     * @param mdocsubmapCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubMap toMdocSubMap(final MdocSubMapCommand mdocsubmapCommand);

    /**
     * entity --> dto
     * @param mdocsubmap
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubMapDto fromMdocSubMap(final MdocSubMap mdocsubmap);

    /**
     * cmd --> dto
     * @param mdocsubmapCommand
     * @return
     */
      MdocSubMapDto fromMdocSubMapCommand(final MdocSubMapCommand mdocsubmapCommand);

    /**
     * do --> dto
     * @param mdocsubmapDo
     * @return
     */
      MdocSubMapDto fromDo(final MdocSubMapDo mdocsubmapDo);

      default MdocSubMapId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubMapId(id);
      }

      default Long id2id(MdocSubMapId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}