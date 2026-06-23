package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;
// RDFA import removed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CreateLogicalPlantResponse implements Serializable {

    private Long logicalPlantId;

    private String logicalPlantNo;


}
