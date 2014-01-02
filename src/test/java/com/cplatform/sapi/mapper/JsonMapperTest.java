package com.cplatform.sapi.mapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: cuikai
 * Date: 13-9-22
 * Time: 下午2:58
 */
public class JsonMapperTest {

    private static final String USER = "{\"USER_ID\":\"222\",\"USER_NAME\":\"name\",\"ADDRESS\":\"1111\"}";
    private JsonMapper mapper;

    @Before
    public void setUp() {
        mapper = JsonMapper.buildUpCaseMapper();
    }

    @Test
    public void toJson() {

        User user = new User();
        user.setAddress("1111");
        user.setUserId("222");
        user.setUserName("name");

        assertEquals(USER, mapper.toJson(user));

    }

    @Test
    public void toBean() {
        User user = mapper.fromJson(USER, User.class);
        assertEquals("1111", user.getAddress());
        assertEquals("222", user.getUserId());
        assertEquals("name", user.getUserName());
    }

    static class User {
        private String userId;
        private String userName;
        private String address;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
