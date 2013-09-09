package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.SysRegionDTO;
import com.cplatform.sapi.DTO.TMemberAddressDTO;
import com.cplatform.sapi.DTO.TMemberAddressesDTO;
import com.cplatform.sapi.DTO.UserAddressDTO;
import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.service.TMemberAddressService;
import com.cplatform.sapi.util.MediaTypes;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午2:35
 */
@Controller
@RequestMapping(value = "/api/v1/address")
public class AddressController {
    private TMemberAddressService memberAddressService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    @ResponseBody
    public TMemberAddressesDTO userAddress(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        Validate.notNull(userId, "用户ID不能为空");
        List<TMemberAddress> addresses = memberAddressService.getAddressByUser(Long.valueOf(userId));

        TMemberAddressesDTO tMemberAddressesDTO = new TMemberAddressesDTO();
        List<TMemberAddressDTO> destinationList = Lists.newArrayList();
        tMemberAddressesDTO.settMemberAddressDTOs(destinationList);
        for (TMemberAddress tMemberAddress : addresses) {
            TMemberAddressDTO tMemberAddressDTO = BeanMapper.map(tMemberAddress, TMemberAddressDTO.class);
            String region = tMemberAddress.getRegion();
            if (null != region) {
                SysRegion province = memberAddressService.getRegionByRegionCode(region.substring(0, 2) + "0000");
                SysRegion city = memberAddressService.getRegionByRegionCode(region.substring(0, 4) + "00");
                SysRegion county = memberAddressService.getRegionByRegionCode(region);
                tMemberAddressDTO.setProvince(BeanMapper.map(province, SysRegionDTO.Data.class));
                tMemberAddressDTO.setCity(BeanMapper.map(city, SysRegionDTO.Data.class));
                tMemberAddressDTO.setCounty(BeanMapper.map(county, SysRegionDTO.Data.class));
            }
            destinationList.add(tMemberAddressDTO);
        }
        return tMemberAddressesDTO;
    }

    @RequestMapping(value = "getAddressInfo", method = RequestMethod.GET)
    @ResponseBody
    public SysRegionDTO getAddressInfo(Long level, Long id) {
        level = (level == null ? 0L : level);
        List<SysRegion> regions = memberAddressService.getRegionByLevel(level, id);
        SysRegionDTO dto = new SysRegionDTO();
        dto.setData(BeanMapper.mapList(regions, SysRegionDTO.Data.class));
        return dto;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteAddress(@PathVariable("id") Long id) {

        memberAddressService.deleteAddress(id);
        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String saveAddress(@RequestBody UserAddressDTO dto) {

        TMemberAddress memberAddress = fillMemberAddress(dto);
        memberAddressService.saveAddress(memberAddress);

        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    private TMemberAddress fillMemberAddress(UserAddressDTO dto) {
        TMemberAddress memberAddress;
        if (dto.getId() == null || StringUtils.isBlank(String.valueOf(dto.getId()))) {
            memberAddress = new TMemberAddress();

        } else {
            memberAddress = memberAddressService.getMemberAddress(dto.getId());
        }
        memberAddress.setDefaultShipping(dto.getDefault());
        memberAddress.setName(dto.getName());
        memberAddress.setZipcode(dto.getZipCode());
        memberAddress.setAddress(dto.getAddress());
        memberAddress.setMobile(dto.getTerminalId());
        memberAddress.setMid(dto.getUserId());
        memberAddress.setCreateTime(TimeUtil.now());
        memberAddress.setRegion(dto.getRegionCode());

        return memberAddress;
    }

    @Autowired
    public void setMemberAddressService(TMemberAddressService memberAddressService) {
        this.memberAddressService = memberAddressService;
    }
}
