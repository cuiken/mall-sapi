package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.SysSegment;
import com.cplatform.sapi.entity.TBossRequest;
import com.cplatform.sapi.repository.SysSegmentDao;
import com.cplatform.sapi.repository.TBossRequestDao;
import com.cplatform.sapi.util.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: cuikai
 * Date: 13-9-24
 * Time: 下午4:24
 */

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private TBossRequestDao bossRequestDao;
    @Mock
    private SysSegmentDao sysSegmentDao;
    @Mock
    private AppConfig appConfig;
    @Mock
    private BusinessLogger businessLogger;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void openRedDiamond() {
        Member user = mock(Member.class);

        OpenRedDiamondStatus status = authService.openRedDiamond(null);
        assertEquals(OpenRedDiamondStatus.USER_NOT_EXIST, status);

        when(user.getRedMember()).thenReturn(1);
        status = authService.openRedDiamond(user);
        assertEquals(OpenRedDiamondStatus.ALREADY_RED_DIAMOND, status);

        when(user.getRedMember()).thenReturn(0);
        when(user.getTerminalId()).thenReturn("13565200040");

        //segment is null
        status = authService.openRedDiamond(user);
        assertEquals(OpenRedDiamondStatus.OPEN_FAILURE, status);

        SysSegment segment = mock(SysSegment.class);
        when(sysSegmentDao.findUniqueBy("segmentCode", StringUtils.substring(user.getTerminalId(), 0, 7)))
                .thenReturn(segment);

        status = authService.openRedDiamond(user);
        assertEquals(OpenRedDiamondStatus.OPEN_SUCCESS, status);
    }
}
