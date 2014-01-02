package com.cplatform.sapi;

import com.cplatform.sapi.service.ItemJsonConvertTest;
import com.cplatform.sapi.service.OrderServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: cuikai
 * Date: 13-9-17
 * Time: 下午1:00
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ItemJsonConvertTest.class, OrderServiceTest.class})
public class TestAll {
}
