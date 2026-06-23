package com.inventory.middle.application.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Component
public class FullAddressHelper {

    public String buildFullAddress(String province, String city, String region, String address) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(province)) sb.append(province);
        if (StringUtils.isNotBlank(city)) sb.append(city);
        if (StringUtils.isNotBlank(region)) sb.append(region);
        if (StringUtils.isNotBlank(address)) sb.append(address);
        return sb.toString();
    }

    public <T> void injectFullAddress(
            List<T> list,
            Function<T, String> provinceGetter,
            Function<T, String> cityGetter,
            Function<T, String> regionGetter,
            Function<T, String> addressGetter,
            BiConsumer<T, String> fullAddressSetter) {
        if (list == null) return;
        for (T item : list) {
            String fullAddress = buildFullAddress(
                    provinceGetter.apply(item),
                    cityGetter.apply(item),
                    regionGetter.apply(item),
                    addressGetter.apply(item));
            fullAddressSetter.accept(item, fullAddress);
        }
    }
}
