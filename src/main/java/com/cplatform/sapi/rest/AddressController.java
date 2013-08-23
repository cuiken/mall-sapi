package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.TMemberAddressDTO;
import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.service.TMemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public List<TMemberAddressDTO> userAddress(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        List<TMemberAddress> address = memberAddressService.getAddressByUser(Long.valueOf(userId == null ? "0" : userId));
        return BeanMapper.mapList(address, TMemberAddressDTO.class);
//        return address;
    }

    @RequestMapping(value = "getAddressInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<SysRegion> getAddressInfo(Long level, String name) {
        level = (level == null ? 0L : level);
        return memberAddressService.getRegionByLevel(level, name);
    }

    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public String deleteAddress(@PathVariable("id") Long id) {

        return "TODO";
    }

    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String saveAddress(HttpServletRequest request) {

        return "TODO";
    }

    @Autowired
    public void setMemberAddressService(TMemberAddressService memberAddressService) {
        this.memberAddressService = memberAddressService;
    }
}
