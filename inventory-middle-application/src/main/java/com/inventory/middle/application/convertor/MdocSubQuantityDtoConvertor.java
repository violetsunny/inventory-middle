package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubQuantityDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-数量DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface MdocSubQuantityDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubquantityDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubQuantity toMdocSubQuantity(final MdocSubQuantityDto mdocsubquantityDto);

    /**
     * dto --> entity
     * @param mdocsubquantityCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubQuantity toMdocSubQuantity(final MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * entity --> dto
     * @param mdocsubquantity
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubQuantityDto fromMdocSubQuantity(final MdocSubQuantity mdocsubquantity);

    /**
     * cmd --> dto
     * @param mdocsubquantityCommand
     * @return
     */
      MdocSubQuantityDto fromMdocSubQuantityCommand(final MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * do --> dto
     * @param mdocsubquantityDo
     * @return
     */
      MdocSubQuantityDto fromDo(final MdocSubQuantityDo mdocsubquantityDo);

      default MdocSubQuantityId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubQuantityId(id);
      }

      default Long id2id(MdocSubQuantityId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}