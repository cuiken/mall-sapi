package com.cplatform.sapi.mapper;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * 特殊对应属性大写，无需在DTO里为每个field设置@JsonProperty
 * mapper.setPropertyNamingStrategy(new CapitalizedPropertyNamingStrategy());
 * <p/>
 * User: cuikai
 * Date: 13-9-22
 * Time: 下午2:27
 */
public class CapitalizedPropertyNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    @Override
    public String translate(String propertyName) {

        if (StringUtils.isAllLowerCase(propertyName)) {
            return StringUtils.upperCase(propertyName);
        } else if (StringUtils.isAllUpperCase(propertyName)) {
            return propertyName;
        } else {
            for (int i = 0; i < propertyName.length(); i++) {
                char c = propertyName.charAt(i);
                if ((c >= 'A') && (c <= 'Z')) {
                    String lowStr = StringUtils.substring(propertyName, 0, i);
                    String upStr = StringUtils.substring(propertyName, i, propertyName.length());
                    return StringUtils.upperCase(lowStr) + "_" + StringUtils.upperCase(upStr);
                }
            }
        }
        return null;
    }

}
