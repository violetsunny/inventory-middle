package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.StorageLocation;
import org.springframework.stereotype.Component;

/**
 * 存储地点表Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class StorageLocationFactory {

	public StorageLocation createStorageLocation() {
		return new StorageLocation();
	}

}