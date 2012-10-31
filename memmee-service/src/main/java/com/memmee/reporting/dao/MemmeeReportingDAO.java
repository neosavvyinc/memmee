package com.memmee.reporting.dao;

import com.memmee.domain.user.dto.ReportingUserMapper;
import com.memmee.domain.user.dto.User;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import javax.xml.ws.soap.MTOM;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 10/30/12
 * Time: 7:39 PM
 */
public interface MemmeeReportingDAO extends Transactional<MemmeeReportingDAO>, GetHandle, CloseMe {

    /**
     *
     * Report Query to find all users that have completed their profile
     *
     * select distinct memmee.user.id, memmee.user.email, coalesce(memmee.user.firstName, 'No Name') as name from memmee.user WHERE memmee.user.firstName <> 'No Name' ORDER BY name
     *
     */
    @SqlQuery("select distinct user.id as id, user.email as email, coalesce(user.firstName, 'No Name') as firstName from user WHERE user.firstName <> 'No Name' ORDER BY firstName")
    @Mapper(ReportingUserMapper.class)
    List<User> getUsersWithCompletedProfiles();

    /**
     *
     * Report Query to find all users that have not completed their profile
     *
     * select distinct memmee.user.id, memmee.user.email, coalesce(memmee.user.firstName, 'No Name') as name from memmee.user WHERE memmee.user.firstName is NULL ORDER BY name
     *
     */
    @SqlQuery("select distinct user.id as id, user.email as email, coalesce(user.firstName, 'No Name') as firstName from user WHERE user.firstName is NULL ORDER BY firstName")
    @Mapper(ReportingUserMapper.class)
    List<User> getUsersWithNoCompletedProfiles();


    /**
    *
    * Report Query to show all users regardless of profile completeness
    *
    * select distinct memmee.user.id, memmee.user.email, coalesce(memmee.user.firstName, '_No Name') as name from memmee.user ORDER BY name;
    *
    */
    @SqlQuery("select distinct user.id as id, user.email as email, coalesce(user.firstName, '_No Name') as firstName from user ORDER BY firstName")
    @Mapper(ReportingUserMapper.class)
    List<User> getUsers();

    /**
     *
     * Report Query to show all users how have memmee'd and how many times
     *
     * select u.firstName, u.email, count(*) as memmeeCount from memmee.memmee m join memmee.user u where m.userId = u.id group by u.id order by memmeeCount DESC, u.firstName, u.email;
     *
     */
    @SqlQuery("select u.id as id, u.firstName as firstName, u.email as email, count(*) as memmeeCount from memmee m join user u where m.userId = u.id group by u.id order by memmeeCount DESC, u.firstName, u.email")
    @Mapper(ReportingUserMapper.class)
    List<User> getUsersWithMemmeeCount();

    /**
     *
     * Report Query to show all users who have not created a memmee yet
     *
     * select u.id, u.firstName, u.email from memmee.user u where u.id not in ( select u.id  from memmee.memmee m join memmee.user u where m.userId = u.id group by u.id );
     *
     */
    @SqlQuery("select u.id as id, u.firstName as firstName, u.email as email from user u where u.id not in ( select u.id  from memmee m join user u where m.userId = u.id group by u.id)" )
    @Mapper(ReportingUserMapper.class)
    List<User> getUsersWithNoMemmees();
}
