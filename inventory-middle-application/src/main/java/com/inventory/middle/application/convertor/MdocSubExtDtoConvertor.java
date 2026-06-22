package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.model.types.MdocSubExtId;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.command.MdocSubExtCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubExtDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-扩展DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface MdocSubExtDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubextDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubExt toMdocSubExt(final MdocSubExtDto mdocsubextDto);

    /**
     * dto --> entity
     * @param mdocsubextCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubExt toMdocSubExt(final MdocSubExtCommand mdocsubextCommand);

    /**
     * entity --> dto
     * @param mdocsubext
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubExtDto fromMdocSubExt(final MdocSubExt mdocsubext);

    /**
     * cmd --> dto
     * @param mdocsubextCommand
     * @return
     */
      MdocSubExtDto fromMdocSubExtCommand(final MdocSubExtCommand mdocsubextCommand);

    /**
     * do --> dto
     * @param mdocsubextDo
     * @return
     */
      MdocSubExtDto fromDo(final MdocSubExtDo mdocsubextDo);

      default MdocSubExtId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubExtId(id);
      }

      default Long id2id(MdocSubExtId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}