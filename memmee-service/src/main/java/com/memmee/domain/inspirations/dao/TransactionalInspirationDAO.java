package com.memmee.domain.inspirations.dao;

import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/24/12
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalInspirationDAO extends Transactional<TransactionalInspirationDAO>, GetHandle, CloseMe {
}
